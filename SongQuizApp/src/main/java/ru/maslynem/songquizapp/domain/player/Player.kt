package ru.maslynem.songquizapp.domain.player

data class Player(
    var id: Int = UNDEFINED_ID,
    var playerName: String
) {

    companion object {
        const val UNDEFINED_ID = -1
    }
}
