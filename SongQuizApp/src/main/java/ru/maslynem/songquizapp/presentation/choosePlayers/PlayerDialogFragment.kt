package ru.maslynem.songquizapp.presentation.choosePlayers

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import ru.maslynem.songquizapp.R
import ru.maslynem.songquizapp.domain.entity.player.Player


class PlayerDialogFragment : DialogFragment() {

    private lateinit var listener: NoticeDialogListener

    private var dialogMode: String = UNDEFINED_MODE
    private var playerName: String = UNDEFINED_NAME
    private var playerId: Int = UNDEFINED_PLAYER_ID


    interface NoticeDialogListener {
        fun onDialogAddModePositiveClick(dialogFragment: DialogFragment, name: String)
        fun onDialogEditModePositiveClick(
            dialogFragment: DialogFragment,
            name: String,
            playerId: Int
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement NoticeDialogListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            var builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogInflater = inflater.inflate(R.layout.dialog_add_player, null)
            val usernameEditText = dialogInflater.findViewById<EditText>(R.id.et_username)
            usernameEditText.requestFocus()
            builder = builder.setView(dialogInflater)
            if (dialogMode == ADD_MODE) {
                builder = builder.setPositiveButton(R.string.add) { _, _ ->
                    val username = usernameEditText.text.toString()
                    if (validateInputName(parseName(username))) {
                        listener.onDialogAddModePositiveClick(
                            this,
                            usernameEditText.text.toString()
                        )
                    }
                    dismiss()
                }
            } else {
                usernameEditText.setText(playerName, TextView.BufferType.EDITABLE)
                usernameEditText.setSelection(usernameEditText.length())
                builder = builder.setPositiveButton(R.string.edit) { _, _ ->
                    val username = usernameEditText.text.toString()
                    if (validateInputName(parseName(username))) {
                        listener.onDialogEditModePositiveClick(
                            this,
                            username,
                            playerId
                        )
                    }
                    dismiss()
                }
            }
            builder.setNegativeButton(R.string.cansel) { _, _ ->
                dialog?.cancel()
            }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        return dialog
    }

    private fun parseParams() {
        val args = requireArguments()
        Log.d("arguments", "$args")
        if (!args.containsKey(DIALOG_MODE)) {
            throw RuntimeException("Param dialog mode is absent")
        }
        val mode = args.getString(DIALOG_MODE)
        if (mode != EDIT_MODE && mode != ADD_MODE) {
            throw RuntimeException("Unknown dialog mode $mode")
        }
        dialogMode = mode

        if (dialogMode == EDIT_MODE) {
            if (!args.containsKey(PLAYER_NAME)) {
                throw RuntimeException("Param player name is absent")
            }
            if (!args.containsKey(PLAYER_ID)) {
                throw RuntimeException("Param player position is absent")
            }
            playerName = args.getString(PLAYER_NAME, UNDEFINED_NAME)
            playerId = args.getInt(PLAYER_ID, UNDEFINED_PLAYER_ID)
        }
    }

    private fun parseName(inputName: String): String {
        return inputName.trim()
    }

    private fun validateInputName(inputName: String): Boolean {
        return inputName.isNotBlank()
    }

    companion object {
        private const val DIALOG_MODE = "dialog_mode"
        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val UNDEFINED_MODE = "undefined_mode"
        private const val PLAYER_NAME = "player_name"
        private const val UNDEFINED_NAME = "undefined"
        private const val PLAYER_ID = "player_id"
        private const val UNDEFINED_PLAYER_ID = -1

        fun newInstanceAddPlayer(): PlayerDialogFragment {
            return PlayerDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(DIALOG_MODE, ADD_MODE)
                }
            }
        }

        fun newInstanceEditPlayer(player: Player): PlayerDialogFragment {
            return PlayerDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(DIALOG_MODE, EDIT_MODE)
                    putString(PLAYER_NAME, player.playerName)
                    putInt(PLAYER_ID, player.id)
                }
            }
        }
    }
}