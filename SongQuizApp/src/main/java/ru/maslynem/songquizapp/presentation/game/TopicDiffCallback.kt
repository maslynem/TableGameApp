package ru.maslynem.songquizapp.presentation.game

import androidx.recyclerview.widget.DiffUtil
import ru.maslynem.songquizapp.domain.topic.Topic

class TopicDiffCallback : DiffUtil.ItemCallback<Topic>() {
    override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem == newItem
    }
}