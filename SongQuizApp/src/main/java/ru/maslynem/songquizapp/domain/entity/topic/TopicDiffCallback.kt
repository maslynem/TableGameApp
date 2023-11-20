package ru.maslynem.songquizapp.domain.entity.topic

import androidx.recyclerview.widget.DiffUtil

class TopicDiffCallback : DiffUtil.ItemCallback<Topic>() {
    override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem == newItem
    }
}