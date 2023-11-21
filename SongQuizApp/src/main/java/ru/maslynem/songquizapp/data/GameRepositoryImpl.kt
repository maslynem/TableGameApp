package ru.maslynem.songquizapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.maslynem.songquizapp.domain.cardUseCase.GameRepository
import ru.maslynem.songquizapp.domain.entity.game.Card
import ru.maslynem.songquizapp.domain.entity.topic.Topic


class GameRepositoryImpl : GameRepository {
    private val topicWithCardNumberListLD = MutableLiveData<List<Topic>>()
    private val topicWithCardNumberList = mutableListOf<Topic>()

    private var cardMap: Map<String, MutableList<Card>> = emptyMap()
    override fun getTopicWithCardNumberList(
        topicList: List<String>,
        cardNumber: Int
    ): LiveData<List<Topic>> {
        cardMap = getCardMapMock(topicList, cardNumber)
        updateCardMap()
        return topicWithCardNumberListLD
    }

    private fun getCardMapMock(
        topicList: List<String>,
        cardNumber: Int
    ): Map<String, MutableList<Card>> {
        return buildMap {
            for (topicName: String in topicList) {
                val tempCardList: MutableList<Card> = mutableListOf()
                val topic = Topic(topicName, cardNumber)
                for (i in 0..cardNumber) {
                    tempCardList.add(Card(topic = topic, word = "${topic.name}$i"))
                }
                topicWithCardNumberList.add(topic)
                put(topic.name, tempCardList)
            }
        }

    }

    override fun removeCard(card: Card) {
        cardMap[card.topic.name]!!.remove(card)
        val topic = topicWithCardNumberList.find { it.name == card.topic.name }
        topic?.let {
            it.cardNumber--
        }
        updateCardMap()
    }

    override fun getCardByTopic(topic: Topic): Card {
        Log.d("getCardByTopic", "Topic $topic, cardMap $cardMap")
        val cards = cardMap[topic.name]!!
        return cards[0]
    }

    private fun updateCardMap() {
        topicWithCardNumberListLD.value = topicWithCardNumberList
    }
}