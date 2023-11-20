package ru.maslynem.songquizapp.di.game

import org.koin.dsl.module
import ru.maslynem.songquizapp.domain.cardUseCase.GetTopicWithCardNumberUseCase
import ru.maslynem.songquizapp.domain.cardUseCase.RemoveCardUseCase

val gameDomainModule = module {
    factory<GetTopicWithCardNumberUseCase> { GetTopicWithCardNumberUseCase(repository = get()) }
    factory<RemoveCardUseCase> { RemoveCardUseCase(repository = get()) }
}