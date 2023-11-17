package ru.maslynem.songquizapp.presentation.songQuizChoosePlayers

import android.os.Bundle
import android.view.View.MeasureSpec
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import ru.maslynem.songquizapp.databinding.ActivitySongQuizChoosePlayerBinding
import ru.maslynem.songquizapp.presentation.songQuizSettings.SettingsActivity


class ChoosePlayerActivity : AppCompatActivity(), AddPlayerDialogFragment.NoticeDialogListener {
    private lateinit var binding: ActivitySongQuizChoosePlayerBinding
    private lateinit var listViewAdapter: PlayerListAdapter
    private val players = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongQuizChoosePlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPlayerListView()
        setupAddButtonClickListener()
        setupNextButtonClick()
    }

    private fun setupPlayerListView() {
        listViewAdapter = PlayerListAdapter(this)
        binding.llPlayers.adapter = listViewAdapter
    }

    private fun setupAddButtonClickListener() {
        binding.btnAdd.setOnClickListener {
            showAddPlayerDialogFragment()
        }
    }

    private fun setupNextButtonClick() {
        binding.btnNext.isEnabled = false
        binding.btnNext.setOnClickListener {
            val intent = SettingsActivity.newIntentWithPlayerList(this, players)
            startActivity(intent)
        }
    }

    private fun showAddPlayerDialogFragment() {
        val dialog = AddPlayerDialogFragment()
        dialog.show(supportFragmentManager, "addPlayerDialogFragment")
    }

    override fun onDialogPositiveClick(dialogFragment: DialogFragment, name: String) {
        if (name.isNotBlank()) {
            players.add(name)
            listViewAdapter.playerList = players
            updateListViewHeightBasedOnChildren()
        }
    }

    private fun updateListViewHeightBasedOnChildren() {
        val listView = binding.llPlayers
        val listAdapter = binding.llPlayers.adapter
        var totalHeight = 0
        val desiredWidth = MeasureSpec.makeMeasureSpec(listView.width, MeasureSpec.AT_MOST)
        for (i in 0 until listAdapter.count) {
            val listItem = listAdapter.getView(i, null, listView)
            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED)
            totalHeight += listItem.measuredHeight
        }

        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
        listView.requestLayout()

    }
}