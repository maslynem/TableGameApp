package ru.maslynem.songquizapp.domain.player

class EditPlayerUseCase(private val playerListRepository: PlayerListRepository) {
    fun editPlayer(player: Player) {
        playerListRepository.editPlayer(player)
    }
}