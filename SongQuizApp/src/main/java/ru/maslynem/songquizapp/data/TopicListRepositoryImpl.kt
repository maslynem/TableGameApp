package ru.maslynem.songquizapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.maslynem.songquizapp.domain.topic.Topic
import ru.maslynem.songquizapp.domain.topic.TopicListRepository

class TopicListRepositoryImpl : TopicListRepository {
    private var topicList = mutableListOf<Topic>()

    init {
        topicList.apply {
            add(Topic("Природа"))
            add(Topic("Профессии"))
            add(Topic("Транспорт"))
            add(Topic("Спорт"))
            add(Topic("Кинофильмы"))
            add(Topic("Аниме"))
            add(Topic("Аниме1"))
            add(Topic("Аниме2"))
            add(Topic("Аниме3"))
            add(Topic("Аниме4"))
            add(Topic("Аниме5"))
        }
    }

    override fun getTopicList(): LiveData<List<Topic>> {
        return MutableLiveData(topicList)
    }
}