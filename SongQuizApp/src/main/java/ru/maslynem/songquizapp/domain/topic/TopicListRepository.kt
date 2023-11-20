package ru.maslynem.songquizapp.domain.topic

import androidx.lifecycle.LiveData

interface TopicListRepository {

    fun getTopicList(): LiveData<List<Topic>>
}