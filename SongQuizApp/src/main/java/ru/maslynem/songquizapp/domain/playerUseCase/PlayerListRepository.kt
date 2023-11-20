package ru.maslynem.songquizapp.domain.playerUseCase

import androidx.lifecycle.LiveData
import ru.maslynem.songquizapp.domain.entity.player.Player

interface PlayerListRepository {
    fun getPlayerList(): LiveData<List<Player>>
    fun addPlayer(player: Player)
    fun editPlayer(player: Player)
    fun deletePlayer(player: Player)
}