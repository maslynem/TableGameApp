package ru.maslynem.songquizapp.di.game

import org.koin.dsl.module
import ru.maslynem.songquizapp.domain.game.GetTopicWithCardNumberUseCase
import ru.maslynem.songquizapp.domain.game.RemoveCardUseCase

val gameDomainModule = module {
    factory<GetTopicWithCardNumberUseCase> { GetTopicWithCardNumberUseCase(repository = get()) }
    factory<RemoveCardUseCase> { RemoveCardUseCase(repository = get()) }
}