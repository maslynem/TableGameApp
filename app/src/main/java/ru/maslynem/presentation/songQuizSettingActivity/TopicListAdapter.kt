package ru.maslynem.presentation.songQuizSettingActivity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import ru.maslynem.mainactivity.R
import ru.maslynem.domain.topic.Topic

class TopicListAdapter :
    RecyclerView.Adapter<TopicListAdapter.TopicViewHolder>() {

    var onTopicClick: ((Topic, Boolean) -> Unit)? = null
    var topicList: List<Topic> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.sq_topic_item,
            parent,
            false
        )
        return TopicViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.topicCheckBox.text = topicList[position].name
        holder.topicCheckBox.isChecked = topicList[position].selected
        holder.topicCheckBox.setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
            if (compoundButton.isPressed) {
                    onTopicClick?.invoke(topicList[position], isChecked)
            }
        }
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    class TopicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topicCheckBox: CheckBox = view.findViewById(R.id.sq_cb_topic)
    }
}