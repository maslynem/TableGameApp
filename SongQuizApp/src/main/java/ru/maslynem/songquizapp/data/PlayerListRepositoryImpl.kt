package ru.maslynem.songquizapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.maslynem.songquizapp.domain.entity.player.Player
import ru.maslynem.songquizapp.domain.playerUseCase.PlayerListRepository

class PlayerListRepositoryImpl : PlayerListRepository {
    private val playerListLD = MutableLiveData<List<Player>>()
    private val playerSet = mutableSetOf<Player>()
    private var autoIncrementId = 0

    override fun getPlayerList(): LiveData<List<Player>> {
        updateList()
        return playerListLD
    }


    override fun addPlayer(player: Player) {
        if (player.id == Player.UNDEFINED_ID) {
            player.id = ++autoIncrementId
        }
        playerSet.add(player)
        updateList()
    }

    override fun editPlayer(player: Player) {
        val oldElement = playerSet.find { it.id == player.id }
        playerSet.remove(oldElement)
        addPlayer(player)
    }

    override fun deletePlayer(player: Player) {
        playerSet.remove(player)
        updateList()
    }

    private fun updateList() {
        playerListLD.value = playerSet.toList()
    }

    override fun resetPlayerScore() {
        playerSet.forEach { player: Player -> player.score = Player.DEFAULT_SCORE }
    }
}