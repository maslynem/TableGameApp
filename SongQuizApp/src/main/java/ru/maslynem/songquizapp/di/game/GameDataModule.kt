package ru.maslynem.songquizapp.di.game

import org.koin.dsl.module
import ru.maslynem.songquizapp.data.GameRepositoryImpl
import ru.maslynem.songquizapp.domain.cardUseCase.GameRepository

val gameDataModule = module {
    single<GameRepository> { (GameRepositoryImpl()) }
}