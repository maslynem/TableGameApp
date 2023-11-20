package ru.maslynem.songquizapp.presentation.choosePlayers

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.maslynem.songquizapp.R
import ru.maslynem.songquizapp.databinding.ActivitySongQuizChoosePlayerBinding
import ru.maslynem.songquizapp.domain.player.Player
import ru.maslynem.songquizapp.presentation.settings.SettingsActivity


class ChoosePlayerActivity : AppCompatActivity(), PlayerDialogFragment.NoticeDialogListener {

    private lateinit var binding: ActivitySongQuizChoosePlayerBinding
    private lateinit var playerListAdapter: PlayerListAdapter
    private val choosePlayerViewModel by viewModel<ChoosePlayerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongQuizChoosePlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupRecyclerView()
        setupAddButtonClickListener()
        setupNextButtonClick()

        addObserversToViewModel()

        if (choosePlayerViewModel.playerList.value?.isEmpty() == true) {
            choosePlayerViewModel.addPlayer(getString(R.string.player_one))
            choosePlayerViewModel.addPlayer(getString(R.string.player_two))
        }
    }

    private fun setupRecyclerView() {
        playerListAdapter = PlayerListAdapter()
        binding.rvPlayerList.adapter = playerListAdapter
        playerListAdapter.onDeleteClick = {
            choosePlayerViewModel.delPlayer(it)
        }
        playerListAdapter.onPlayerClick = {
            showEditPlayerDialogFragment(it)
        }

        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val player = playerListAdapter.currentList[viewHolder.adapterPosition]
                choosePlayerViewModel.delPlayer(player)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvPlayerList)
    }

    private fun addObserversToViewModel() {
        choosePlayerViewModel.playerList.observe(this) {
            Log.d("activity", "$it")
            playerListAdapter.submitList(it)
        }

        choosePlayerViewModel.shouldDisableAddBtn.observe(this) {
            binding.btnAdd.isEnabled = !it
        }

        choosePlayerViewModel.shouldEnableNextBtn.observe(this) {
            binding.btnNext.isEnabled = it
        }
    }

    private fun setupAddButtonClickListener() {
        binding.btnAdd.setOnClickListener {
            showAddPlayerDialogFragment()
        }
    }

    private fun setupNextButtonClick() {
        binding.btnNext.isEnabled = false
        binding.btnNext.setOnClickListener {
            val playerList = choosePlayerViewModel.playerList.value
            playerList?.let {
                val intent = SettingsActivity.newIntent(this)
                startActivity(intent)
            }
        }
    }

    private fun showAddPlayerDialogFragment() {
        val dialog = PlayerDialogFragment.newInstanceAddPlayer()
        dialog.show(supportFragmentManager, "addPlayerDialogFragment")
    }
    private fun showEditPlayerDialogFragment(player: Player) {
        val dialog = PlayerDialogFragment.newInstanceEditPlayer(player)
        dialog.show(supportFragmentManager, "editPlayerDialogFragment")
    }

    override fun onDialogAddModePositiveClick(dialogFragment: DialogFragment, name: String) {
        if (name.isNotBlank()) {
            choosePlayerViewModel.addPlayer(name)
        }
    }

    override fun onDialogEditModePositiveClick(
        dialogFragment: DialogFragment,
        name: String,
        playerId: Int
    ) {
        if (name.isNotBlank()) {
            choosePlayerViewModel.editPlayer(name, playerId)
        }
    }
}