package ru.maslynem.songquizapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.maslynem.songquizapp.presentation.songQuizChoosePlayers.ChoosePlayerViewModel
import ru.maslynem.songquizapp.presentation.songQuizSettings.SettingsViewModel


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
}


