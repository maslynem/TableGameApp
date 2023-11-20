package ru.maslynem.songquizapp.presentation.game

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.maslynem.songquizapp.R
import ru.maslynem.songquizapp.domain.topic.Topic


class TopicWithCardNumberListAdapter :
    ListAdapter<Topic, TopicWithCardNumberListAdapter.TopicWithCardNumberViewHolder>(
        TopicDiffCallback()
    ) {

    var onTopicClick: ((topic: Topic) -> Unit)? = null

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

    override fun onBindViewHolder(holder: TopicWithCardNumberViewHolder, position: Int) {
        val topic = getItem(position)
        holder.topicCardCount.text = topic.cardNumber.toString()
        holder.topicName.text = topic.name.uppercase()
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