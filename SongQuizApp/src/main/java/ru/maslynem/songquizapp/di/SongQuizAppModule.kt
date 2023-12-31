package ru.maslynem.songquizapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.maslynem.songquizapp.presentation.choosePlayers.ChoosePlayerViewModel
import ru.maslynem.songquizapp.presentation.game.cardActivity.CardViewModel
import ru.maslynem.songquizapp.presentation.game.gameActivity.GameViewModel
import ru.maslynem.songquizapp.presentation.settings.SettingsViewModel


val appModule = module {
    viewModel<ChoosePlayerViewModel> {
        ChoosePlayerViewModel(
            addPlayerUseCase = get(),
            deletePlayerUseCase = get(),
            editPlayerUseCase = get(),
            getPlayerListUseCase = get()
        )
    }

    viewModel<SettingsViewModel> {
        SettingsViewModel(getTopicListUseCase = get())
    }

    viewModel<GameViewModel> {
        GameViewModel(
            getPlayerListUseCase = get(),
            getTopicWithCardNumberUseCase = get(),
            resetPlayerScoreUseCase = get()
        )
    }

    viewModel<CardViewModel>() {
        CardViewModel(
            getCardByTopicUseCase = get(),
            removeCardUseCase = get(),
            editPlayerUseCase = get(),
            getPlayerListUseCase = get()
        )
    }
}


