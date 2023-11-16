package ru.maslynem.domain

import ru.maslynem.domain.topic.Topic

class GetTopicListUseCase {

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

    fun getTopicList(): List<Topic> {
        return topicList
    }
}