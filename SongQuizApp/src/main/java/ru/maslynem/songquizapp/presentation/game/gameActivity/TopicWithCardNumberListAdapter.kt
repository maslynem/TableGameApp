package ru.maslynem.songquizapp.presentation.game.gameActivity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.maslynem.songquizapp.R
import ru.maslynem.songquizapp.domain.entity.topic.Topic
import ru.maslynem.songquizapp.presentation.settings.TopicListAdapter


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
        val layout = when (viewType) {
            TopicListAdapter.VIEW_TYPE_ENABLED -> R.layout.game_topic_with_card_item_enabled
            TopicListAdapter.VIEW_TYPE_DISABLED -> R.layout.game_topic_with_card_item_disabled
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return TopicWithCardNumberViewHolder(view)
    }

    override fun getItemCount(): Int = topicList.size

    override fun getItemViewType(position: Int): Int {
        val item = topicList[position]
        return if (item.cardNumber != 0) {
            TopicListAdapter.VIEW_TYPE_ENABLED
        } else TopicListAdapter.VIEW_TYPE_DISABLED
    }

    override fun onBindViewHolder(holder: TopicWithCardNumberViewHolder, position: Int) {
        val topic = topicList[position]
        holder.topicCardCount.text = topic.cardNumber.toString()
        holder.topicName.text = topic.name.uppercase()
        holder.topicName.setOnClickListener { onTopicClick?.invoke(topic) }
        holder.topicName.isEnabled = topic.cardNumber != 0
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