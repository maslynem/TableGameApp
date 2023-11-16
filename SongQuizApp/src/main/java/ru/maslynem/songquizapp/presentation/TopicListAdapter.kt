package ru.maslynem.songquizapp.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import ru.maslynem.songquizapp.R
import ru.maslynem.songquizapp.presentation.topic.TopicCheckBox

class TopicListAdapter :
    RecyclerView.Adapter<TopicListAdapter.TopicViewHolder>() {

    var onTopicClick: ((TopicCheckBox, Boolean) -> Unit)? = null
    var topicCheckBoxList: List<TopicCheckBox> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.topic_item_enabled
            VIEW_TYPE_DISABLED -> R.layout.topic_item_disabled
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return TopicViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.topicCheckBox.text = topicCheckBoxList[position].name
        holder.topicCheckBox.isChecked = topicCheckBoxList[position].selected
        holder.topicCheckBox.isEnabled = topicCheckBoxList[position].enabled
        holder.topicCheckBox.setOnCheckedChangeListener { compoundButton: CompoundButton, isChecked: Boolean ->
            if (compoundButton.isPressed) {
                onTopicClick?.invoke(topicCheckBoxList[position], isChecked)
            }
        }
    }

    override fun getItemCount(): Int {
        return topicCheckBoxList.size
    }

    override fun getItemViewType(position: Int): Int {
        val item = topicCheckBoxList[position]
        return if (item.enabled) {
            VIEW_TYPE_ENABLED
        } else VIEW_TYPE_DISABLED
    }

    class TopicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topicCheckBox: CheckBox = view.findViewById(R.id.sq_cb_topic)
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 2
        const val VIEW_TYPE_DISABLED = 4
    }
}