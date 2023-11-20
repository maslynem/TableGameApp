package ru.maslynem.songquizapp.di.choosePlayer

import org.koin.dsl.module
import ru.maslynem.songquizapp.data.PlayerListRepositoryImpl
import ru.maslynem.songquizapp.domain.playerUseCase.PlayerListRepository

val choosePlayerDataModule = module {
    single<PlayerListRepository> { PlayerListRepositoryImpl() }
}