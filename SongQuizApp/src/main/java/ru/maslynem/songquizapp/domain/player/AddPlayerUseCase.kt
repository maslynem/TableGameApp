package ru.maslynem.songquizapp.domain.player

class AddPlayerUseCase(private val playerListRepository: PlayerListRepository) {
    fun addPlayer(player: Player) {
        playerListRepository.addPlayer(player)
    }
}