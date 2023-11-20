package ru.maslynem.songquizapp.domain.entity.player

import androidx.recyclerview.widget.DiffUtil

class PlayerDiffCallback: DiffUtil.ItemCallback<Player>() {
    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem == newItem
    }
}