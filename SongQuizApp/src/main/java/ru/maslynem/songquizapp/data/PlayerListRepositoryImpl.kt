package ru.maslynem.songquizapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.maslynem.songquizapp.domain.entity.player.Player
import ru.maslynem.songquizapp.domain.playerUseCase.PlayerListRepository

class PlayerListRepositoryImpl : PlayerListRepository {
    private val playerListLD = MutableLiveData<List<Player>>()
    private val playerList = mutableListOf<Player>()
    private var autoIncrementId = 0

    override fun getPlayerList(): LiveData<List<Player>> {
        updateList()
        return playerListLD
    }


    override fun addPlayer(player: Player) {
        if (player.id == Player.UNDEFINED_ID) {
            player.id = ++autoIncrementId
        }
        playerList.add(player)
        updateList()
    }

    override fun editPlayer(player: Player) {
        val oldElement = playerList.find { it.id == player.id }
        playerList.remove(oldElement)
        addPlayer(player)
    }

    override fun deletePlayer(player: Player) {
        playerList.remove(player)
        updateList()
    }

    private fun updateList() {
        playerListLD.value = playerList.toList()
    }

    override fun resetPlayerScore() {
        playerList.forEach { player: Player -> player.score = Player.DEFAULT_SCORE }
    }
}