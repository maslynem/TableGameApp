package ru.maslynem.songquizapp.presentation.songQuizChoosePlayers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.maslynem.songquizapp.data.PlayerListRepositoryImpl
import ru.maslynem.songquizapp.domain.player.AddPlayerUseCase
import ru.maslynem.songquizapp.domain.player.DeletePlayerUseCase
import ru.maslynem.songquizapp.domain.player.EditPlayerUseCase
import ru.maslynem.songquizapp.domain.player.GetPlayerListUseCase
import ru.maslynem.songquizapp.domain.player.Player

class ChoosePlayerViewModel : ViewModel() {

    private val repository = PlayerListRepositoryImpl()

    private val addPlayerUseCase = AddPlayerUseCase(repository)
    private val deletePlayerUseCase = DeletePlayerUseCase(repository)
    private val editPlayerUseCase = EditPlayerUseCase(repository)
    private val getPlayerListUseCase = GetPlayerListUseCase(repository)


    private var _shouldEnableNextBtn = MutableLiveData<Boolean>()
    val shouldEnableNextBtn: LiveData<Boolean>
        get() = _shouldEnableNextBtn

    private var _shouldDisableAddBtn = MutableLiveData<Boolean>()
    val shouldDisableAddBtn: LiveData<Boolean>
        get() = _shouldDisableAddBtn

    val playerList = getPlayerListUseCase.getPlayerList()

    fun delPlayer(player: Player) {
        deletePlayerUseCase.deletePlayer(player)
        checkSizeOfList()
    }

    fun addPlayer(name: String) {
       addPlayerUseCase.addPlayer(Player(playerName = name))
        checkSizeOfList()
    }

    fun editPlayer(name: String, playerId: Int) {
       editPlayerUseCase.editPlayer(Player(id = playerId, playerName = name))
    }

    private fun checkSizeOfList() {
        _shouldEnableNextBtn.value = playerList.value?.size!! >= MIN_PLAYERS_NUMBER
        _shouldDisableAddBtn.value = playerList.value?.size == MAX_PLAYERS_NUMBER
    }

    companion object {
        private const val MAX_PLAYERS_NUMBER = 6
        private const val MIN_PLAYERS_NUMBER = 2
    }

}