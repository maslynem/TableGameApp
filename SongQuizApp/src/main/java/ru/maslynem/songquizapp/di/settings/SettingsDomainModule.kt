package ru.maslynem.songquizapp.di.settings

import org.koin.dsl.module
import ru.maslynem.songquizapp.domain.topic.GetTopicListUseCase

val settingsDomainModule = module {
    factory<GetTopicListUseCase> { GetTopicListUseCase(topicListRepository = get()) }
}