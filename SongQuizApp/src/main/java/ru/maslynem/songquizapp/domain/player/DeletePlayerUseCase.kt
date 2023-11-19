package ru.maslynem.songquizapp.domain.player

class DeletePlayerUseCase(private val playerListRepository: PlayerListRepository) {
    fun deletePlayer(player: Player) {
        playerListRepository.deletePlayer(player)
    }
}