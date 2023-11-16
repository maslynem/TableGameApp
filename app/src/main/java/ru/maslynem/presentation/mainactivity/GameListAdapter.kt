package ru.maslynem.presentation.mainactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.maslynem.mainactivity.R

class GameListAdapter(private val gameList: List<String>) :
    RecyclerView.Adapter<GameListAdapter.GameNameViewHolder>() {

    var onGameClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameNameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.game_item,
            parent,
            false
        )
        return GameNameViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: GameNameViewHolder, position: Int) {
        val gameName = gameList[position]
        viewHolder.game.text = gameName
        viewHolder.game.setOnClickListener{
            onGameClick?.invoke(gameName)
        }
    }

    override fun getItemCount(): Int {
       return gameList.size
    }


    class GameNameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val game: TextView = view.findViewById(R.id.game)
    }
}