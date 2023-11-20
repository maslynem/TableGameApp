package ru.maslynem.songquizapp.presentation.choosePlayers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.maslynem.songquizapp.R
import ru.maslynem.songquizapp.domain.entity.player.Player
import ru.maslynem.songquizapp.domain.entity.player.PlayerDiffCallback


class PlayerListAdapter :
   ListAdapter<Player, PlayerListAdapter.PlayerItemViewHolder>(PlayerDiffCallback()) {

    var onDeleteClick: ((player: Player) -> Unit)? = null

    var onPlayerClick: ((player: Player) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.player_item,
            parent,
            false
        )
        return PlayerItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerItemViewHolder, position: Int) {
        val player = getItem(position)
        holder.playerUserName.text = player.playerName
        holder.delBtn.setOnClickListener { onDeleteClick?.invoke(player) }
        holder.playerUserName.setOnClickListener { onPlayerClick?.invoke(player) }
    }

    override fun onViewRecycled(holder: PlayerItemViewHolder) {
        super.onViewRecycled(holder)
        holder.playerUserName.text = ""
    }

    class PlayerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val playerUserName: TextView = view.findViewById(R.id.tv_player_name)
        val delBtn: Button = view.findViewById(R.id.btn_del_player)
    }
}