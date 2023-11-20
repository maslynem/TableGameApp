package ru.maslynem.songquizapp.presentation.game.gameActivity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.maslynem.songquizapp.R
import ru.maslynem.songquizapp.domain.entity.player.Player

class PlayerScoreListAdapter :
    RecyclerView.Adapter<PlayerScoreListAdapter.PlayerScoreViewHolder>() {

    var playerList: List<Player> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.player_score_item,
            parent,
            false
        )
        return PlayerScoreViewHolder(view)
    }

    override fun getItemCount(): Int = playerList.size


    override fun onBindViewHolder(holder: PlayerScoreViewHolder, position: Int) {
        holder.playerName.text = playerList[position].playerName
        holder.playerScore.text = playerList[position].score.toString()
    }


    class PlayerScoreViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val playerName: TextView = view.findViewById(R.id.tv_player_name)
        val playerScore: TextView = view.findViewById(R.id.tv_player_score)
    }

}