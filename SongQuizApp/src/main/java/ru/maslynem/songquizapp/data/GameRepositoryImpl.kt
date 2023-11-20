package ru.maslynem.songquizapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.maslynem.songquizapp.domain.game.Card
import ru.maslynem.songquizapp.domain.game.GameRepository
import ru.maslynem.songquizapp.domain.topic.Topic


class GameRepositoryImpl : GameRepository {
    private val topicWithCardNumberList = MutableLiveData<List<Topic>>()
    private var cardMap: Map<Topic, MutableList<Card>> = emptyMap()
    override fun getTopicWithCardNumberList(
        topicList: List<String>,
        cardNumber: Int
    ): LiveData<List<Topic>> {
        cardMap = getCardMapMock(topicList, cardNumber)
        return MutableLiveData(cardMap.keys.toList())
    }

    private fun getCardMapMock(
        topicList: List<String>,
        cardNumber: Int
    ): Map<Topic, MutableList<Card>> {
        return buildMap {
            for (topicName: String in topicList) {
                val tempCardList: MutableList<Card> = mutableListOf()
                val topic = Topic(topicName, cardNumber)
                for (i in 0..cardNumber) {
                    tempCardList.add(Card(topic = topic, word = "$topic$i"))
                }
                put(topic, tempCardList)
            }
        }

    }

    override fun removeCard(card: Card) {
        cardMap[card.topic]!!.remove(card)
        cardMap.keys.find { it == card.topic }!!.cardNumber.dec()
        updateCardMap()
    }

    private fun updateCardMap() {
        topicWithCardNumberList.value = cardMap.keys.toList()
    }
}