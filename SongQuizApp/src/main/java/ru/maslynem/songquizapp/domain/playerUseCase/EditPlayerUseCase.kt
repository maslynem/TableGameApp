package ru.maslynem.songquizapp.domain.playerUseCase

import ru.maslynem.songquizapp.domain.entity.player.Player

class EditPlayerUseCase(private val playerListRepository: PlayerListRepository) {
    fun editPlayer(player: Player) {
        playerListRepository.editPlayer(player)
    }
}