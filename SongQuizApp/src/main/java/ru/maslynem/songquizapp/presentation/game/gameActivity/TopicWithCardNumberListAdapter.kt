package ru.maslynem.songquizapp.presentation.game.gameActivity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.maslynem.songquizapp.R
import ru.maslynem.songquizapp.domain.entity.topic.Topic


class TopicWithCardNumberListAdapter :
    RecyclerView.Adapter<TopicWithCardNumberListAdapter.TopicWithCardNumberViewHolder>() {

    var onTopicClick: ((topic: Topic) -> Unit)? = null

    var topicList: List<Topic> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopicWithCardNumberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.game_topic_with_card_item_enabled,
            parent,
            false
        )
        return TopicWithCardNumberViewHolder(view)
    }

    override fun getItemCount(): Int = topicList.size


    override fun onBindViewHolder(holder: TopicWithCardNumberViewHolder, position: Int) {
        val topic = topicList[position]
        holder.topicCardCount.text = topic.cardNumber.toString()
        holder.topicName.text = topic.name.uppercase()
        holder.topicName.setOnClickListener { onTopicClick?.invoke(topic) }
    }

    override fun onViewRecycled(holder: TopicWithCardNumberViewHolder) {
        super.onViewRecycled(holder)
        holder.topicName.text = ""
    }

    class TopicWithCardNumberViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val topicName: TextView = view.findViewById(R.id.tv_topic_with_card)
        val topicCardCount: TextView = view.findViewById(R.id.tv_topic_with_card_number)

    }
}