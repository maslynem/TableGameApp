package ru.maslynem.songquizapp.domain.topic

import androidx.lifecycle.LiveData

class GetTopicListUseCase(private val topicListRepository: TopicListRepository) {


    fun getTopicList(): LiveData<List<Topic>> {
        return topicListRepository.getTopicList()
    }
}