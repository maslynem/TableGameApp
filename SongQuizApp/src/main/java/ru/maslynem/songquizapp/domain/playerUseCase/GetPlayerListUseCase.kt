package ru.maslynem.songquizapp.domain.playerUseCase

import androidx.lifecycle.LiveData
import ru.maslynem.songquizapp.domain.entity.player.Player

class GetPlayerListUseCase(private val playerListRepository: PlayerListRepository) {
    fun getPlayerList(): LiveData<List<Player>> {
        return playerListRepository.getPlayerList()
    }
}