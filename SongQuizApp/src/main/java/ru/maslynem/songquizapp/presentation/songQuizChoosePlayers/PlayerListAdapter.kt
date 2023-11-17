package ru.maslynem.songquizapp.presentation.songQuizChoosePlayers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import ru.maslynem.songquizapp.R


class PlayerListAdapter(context: Context) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)
    var playerList = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getCount(): Int {
        return playerList.size
    }

    override fun getItem(position: Int): Any {
        return playerList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = inflater.inflate(R.layout.player_item, null)
        val userNameTextView = view.findViewById<TextView>(R.id.player_name)
        userNameTextView.text = playerList[position]
        return view
    }

}