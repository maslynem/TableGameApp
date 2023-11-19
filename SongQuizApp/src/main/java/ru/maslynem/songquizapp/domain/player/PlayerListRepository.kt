package ru.maslynem.songquizapp.domain.player

import androidx.lifecycle.LiveData

interface PlayerListRepository {
    fun getPlayerList(): LiveData<List<Player>>
    fun addPlayer(player: Player)
    fun editPlayer(player: Player)
    fun deletePlayer(player: Player)
}