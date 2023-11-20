package ru.maslynem.songquizapp.domain.topicUseCase

import androidx.lifecycle.LiveData
import ru.maslynem.songquizapp.domain.entity.topic.Topic

class GetTopicListUseCase(private val topicListRepository: TopicListRepository) {


    fun getTopicList(): LiveData<List<Topic>> {
        return topicListRepository.getTopicList()
    }
}