package ru.maslynem.songquizapp.data

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import ru.maslynem.songquizapp.domain.entity.topic.Topic
import ru.maslynem.songquizapp.domain.topicUseCase.TopicListRepository
import java.util.concurrent.TimeUnit


class TopicListRepositoryImpl : TopicListRepository {
    private val url = "https://soundquizservice.onrender.com/api/v1"
    private val client = OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build()

    private var topicList = mutableListOf<Topic>()

    init {
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        performGetRequest()
    }

    private fun performGetRequest() {
        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        val response = client.newCall(request).execute()
        parseResponse(response)
    }

    private fun parseResponse(response: Response) {
        response.body?.let {
            topicList.clear()
            val obj = JSONObject(it.string())
            val count = obj.getInt("count")
            val topics = obj.getJSONArray("topics")
            for (i in 0 until count) {
                topicList.add(Topic(topics.getString(i)))
            }
        }
    }


    override fun getTopicList(): LiveData<List<Topic>> {
        return MutableLiveData(topicList)
    }
}