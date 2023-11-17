package ru.maslynem.presentation.mainactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import ru.maslynem.mainactivity.R
import ru.maslynem.songquizapp.presentation.songQuizChoosePlayers.ChoosePlayerActivity

class MainScreenActivity : AppCompatActivity() {
    private lateinit var gameAdapter: GameListAdapter
    private lateinit var gameList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        gameList = resources.getStringArray(R.array.gameNames).toList()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val rvGameName = findViewById<RecyclerView>(R.id.rv_game_list)
        gameAdapter = GameListAdapter(gameList)
        rvGameName.adapter = gameAdapter
        gameAdapter.onGameClick = {
           if (it == "Звуковая викторина") {
               val intent = Intent(this, ChoosePlayerActivity::class.java)
               startActivity(intent)
           }
        }
    }
}