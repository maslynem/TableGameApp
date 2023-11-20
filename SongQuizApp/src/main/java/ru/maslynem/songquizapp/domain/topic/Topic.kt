package ru.maslynem.songquizapp.domain.topic

data class Topic(
    val name: String,
    var cardNumber: Int = UNDEFINED_CARD_NUMBER
) {
    companion object {
        const val UNDEFINED_CARD_NUMBER = -1
    }
}
