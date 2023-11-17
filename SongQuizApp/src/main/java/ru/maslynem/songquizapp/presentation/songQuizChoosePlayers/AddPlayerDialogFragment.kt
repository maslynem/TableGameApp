package ru.maslynem.songquizapp.presentation.songQuizChoosePlayers

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import ru.maslynem.songquizapp.R

class AddPlayerDialogFragment : DialogFragment() {

    private lateinit var listener: NoticeDialogListener
    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialogFragment: DialogFragment, name: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement NoticeDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val dialogInflater = inflater.inflate(R.layout.dialog_add_player, null)
            val usernameEditText = dialogInflater.findViewById<EditText>(R.id.username)
            usernameEditText.requestFocus()
            builder.setView(dialogInflater)
                .setPositiveButton(R.string.add) { _, _ ->
                    listener.onDialogPositiveClick(this, usernameEditText.text.toString())
                    dismiss()
                }
                .setNegativeButton(R.string.cansel) { _, _ ->
                    dialog?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
        return dialog
    }
}