package ru.maslynem.songquizapp.domain.playerUseCase

import ru.maslynem.songquizapp.domain.entity.player.Player

class AddPlayerUseCase(private val playerListRepository: PlayerListRepository) {
    fun addPlayer(player: Player) {
        playerListRepository.addPlayer(player)
    }
}