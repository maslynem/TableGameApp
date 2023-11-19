package ru.maslynem.songquizapp.domain.player

import androidx.lifecycle.LiveData

class GetPlayerListUseCase(private val playerListRepository: PlayerListRepository) {
    fun getPlayerList(): LiveData<List<Player>> {
        return playerListRepository.getPlayerList()
    }
}