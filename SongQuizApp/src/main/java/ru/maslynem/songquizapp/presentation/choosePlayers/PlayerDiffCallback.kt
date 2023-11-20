package ru.maslynem.songquizapp.presentation.choosePlayers

import androidx.recyclerview.widget.DiffUtil
import ru.maslynem.songquizapp.domain.player.Player

class PlayerDiffCallback: DiffUtil.ItemCallback<Player>() {
    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem == newItem
    }
}