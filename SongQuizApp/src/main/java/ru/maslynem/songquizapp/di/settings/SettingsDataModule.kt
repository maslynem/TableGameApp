package ru.maslynem.songquizapp.di.settings

import org.koin.dsl.module
import ru.maslynem.songquizapp.data.TopicListRepositoryImpl
import ru.maslynem.songquizapp.domain.topicUseCase.TopicListRepository

val settingsDataModule = module {
    single<TopicListRepository> { TopicListRepositoryImpl() }
}