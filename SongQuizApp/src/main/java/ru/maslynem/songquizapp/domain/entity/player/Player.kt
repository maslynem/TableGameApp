package ru.maslynem.songquizapp.domain.entity.player

data class Player(
    var id: Int = UNDEFINED_ID,
    var playerName: String,
    var score: Int = DEFAULT_SCORE,
) {

    companion object {
        const val UNDEFINED_ID = -1
        const val DEFAULT_SCORE = 0
    }
}
