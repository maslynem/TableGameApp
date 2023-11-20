package ru.maslynem.songquizapp.di.choosePlayer

import org.koin.dsl.module
import ru.maslynem.songquizapp.domain.playerUseCase.AddPlayerUseCase
import ru.maslynem.songquizapp.domain.playerUseCase.DeletePlayerUseCase
import ru.maslynem.songquizapp.domain.playerUseCase.EditPlayerUseCase
import ru.maslynem.songquizapp.domain.playerUseCase.GetPlayerListUseCase

val choosePlayerDomainModule = module {
    factory<AddPlayerUseCase> { AddPlayerUseCase(playerListRepository = get()) }
    factory<DeletePlayerUseCase> { DeletePlayerUseCase(playerListRepository = get()) }
    factory<EditPlayerUseCase> { EditPlayerUseCase(playerListRepository = get()) }
    factory<GetPlayerListUseCase> { GetPlayerListUseCase(playerListRepository = get()) }
}