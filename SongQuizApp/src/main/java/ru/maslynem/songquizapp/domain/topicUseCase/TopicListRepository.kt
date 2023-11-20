package ru.maslynem.songquizapp.domain.topicUseCase

import androidx.lifecycle.LiveData
import ru.maslynem.songquizapp.domain.entity.topic.Topic

interface TopicListRepository {

    fun getTopicList(): LiveData<List<Topic>>
}