package ru.maslynem.songquizapp.data

import android.os.StrictMode
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import ru.maslynem.songquizapp.domain.cardUseCase.GameRepository
import ru.maslynem.songquizapp.domain.entity.game.Card
import ru.maslynem.songquizapp.domain.entity.topic.Topic
import java.util.concurrent.TimeUnit


class GameRepositoryImpl : GameRepository {
    private val url = "https://soundquizservice.onrender.com/api/v1"
    private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()
    private val client = OkHttpClient.Builder().connectTimeout(5, TimeUnit.MINUTES).build()

    private val topicWithCardNumberListLD = MutableLiveData<List<Topic>>()
    private val topicWithCardNumberList = mutableListOf<Topic>()

    private var cardMap: Map<String, MutableList<Card>> = emptyMap()

    init {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }

    override fun getTopicWithCardNumberList(
        topicList: List<String>,
        cardNumber: Int
    ): LiveData<List<Topic>> {
        performPostRequest(topicList, cardNumber)
        updateCardMap()
        return topicWithCardNumberListLD
    }

    private fun performPostRequest(
        topicList: List<String>,
        cardNumber: Int
    ) {
        val jsonArray = JSONArray()
        for (topic: String in topicList) {
            jsonArray.put(topic)
        }
        val jsonObject = JSONObject().apply {
            put("questionsNumber", cardNumber)
            put("topics", jsonArray)
        }
        Log.d("JSON", "$jsonObject")
        val request = Request.Builder()
            .post(jsonObject.toString().toRequestBody(JSON_MEDIA_TYPE))
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw RuntimeException("Error during post request ${response.body}")
            }
            parseResponse(response, topicList, cardNumber)
        }
    }

    private fun parseResponse(response: Response, topicList: List<String>, cardNumber: Int) {
        response.body?.let {
            val responseJson = JSONObject(it.string())

            topicWithCardNumberList.clear()

            cardMap = buildMap {
                for (topicName: String in topicList) {
                    val cardArray = responseJson.getJSONArray(topicName)

                    val tempCardList: MutableList<Card> = mutableListOf()
                    val topic = Topic(topicName, cardNumber)

                    for (i in 0 until cardNumber) {
                        tempCardList.add(Card(topic = topic, word = cardArray.getString(i)))
                    }
                    topicWithCardNumberList.add(topic)
                    put(topic.name, tempCardList)
                }
            }
        }
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