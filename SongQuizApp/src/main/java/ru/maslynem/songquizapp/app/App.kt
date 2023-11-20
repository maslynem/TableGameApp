package ru.maslynem.songquizapp.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.maslynem.songquizapp.di.appModule
import ru.maslynem.songquizapp.di.choosePlayer.choosePlayerDataModule
import ru.maslynem.songquizapp.di.choosePlayer.choosePlayerDomainModule
import ru.maslynem.songquizapp.di.game.gameDataModule
import ru.maslynem.songquizapp.di.game.gameDomainModule
import ru.maslynem.songquizapp.di.settings.settingsDataModule
import ru.maslynem.songquizapp.di.settings.settingsDomainModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    choosePlayerDataModule,
                    choosePlayerDomainModule,
                    settingsDataModule,
                    settingsDomainModule,
                    gameDataModule,
                    gameDomainModule
                )
            )
        }
    }

}