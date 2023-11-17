package ru.maslynem.songquizapp.presentation.songQuizChoosePlayers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChoosePlayerViewModel : ViewModel() {
    private var _shouldEnableNextBtn = MutableLiveData<Boolean>()
    val shouldEnableNextBtn: LiveData<Boolean>
        get() = _shouldEnableNextBtn


    private var _playerList = MutableLiveData<List<String>>()
    val playerList: LiveData<List<String>>
        get() = _playerList



}