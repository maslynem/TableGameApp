package ru.maslynem.songquizapp.domain.entity.topic

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Topic(
    val name: String,
    var cardNumber: Int = UNDEFINED_CARD_NUMBER
) : Parcelable {
    companion object {
        const val UNDEFINED_CARD_NUMBER = -1
    }
}
