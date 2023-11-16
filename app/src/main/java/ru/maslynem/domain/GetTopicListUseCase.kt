package ru.maslynem.domain

import ru.maslynem.domain.topic.Topic

class GetTopicListUseCase {

    private var topicList = mutableListOf<Topic>()

    init {
        topicList.apply {
            add(Topic("Транспорт", false))
            add(Topic("Природа", false))
            add(Topic("Профессии", false))
            add(Topic("Спорт", false))
            add(Topic("Кинофильмы", false))
            add(Topic("Аниме", false))
            add(Topic("Аниме1", false))
            add(Topic("Аниме2", false))
            add(Topic("Аниме3", false))
            add(Topic("Аниме4", false))
            add(Topic("Аниме5", false))
        }
    }

    fun getTopicList(): List<Topic> {
        return topicList
    }
}