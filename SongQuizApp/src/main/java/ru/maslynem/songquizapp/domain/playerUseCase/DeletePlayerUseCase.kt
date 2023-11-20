package ru.maslynem.songquizapp.domain.playerUseCase

import ru.maslynem.songquizapp.domain.entity.player.Player

class DeletePlayerUseCase(private val playerListRepository: PlayerListRepository) {
    fun deletePlayer(player: Player) {
        playerListRepository.deletePlayer(player)
    }
}