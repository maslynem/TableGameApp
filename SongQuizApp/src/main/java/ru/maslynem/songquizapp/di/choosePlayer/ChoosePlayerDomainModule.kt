package ru.maslynem.songquizapp.di.choosePlayer

import org.koin.dsl.module
import ru.maslynem.songquizapp.domain.player.AddPlayerUseCase
import ru.maslynem.songquizapp.domain.player.DeletePlayerUseCase
import ru.maslynem.songquizapp.domain.player.EditPlayerUseCase
import ru.maslynem.songquizapp.domain.player.GetPlayerListUseCase

val choosePlayerDomainModule = module {
    factory<AddPlayerUseCase> { AddPlayerUseCase(playerListRepository = get()) }
    factory<DeletePlayerUseCase> { DeletePlayerUseCase(playerListRepository = get()) }
    factory<EditPlayerUseCase> { EditPlayerUseCase(playerListRepository = get()) }
    factory<GetPlayerListUseCase> { GetPlayerListUseCase(playerListRepository = get()) }
}