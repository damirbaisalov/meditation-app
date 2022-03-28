package com.example.meditation.dialog_fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.meditation.R

class DialogFragmentNameSurname: DialogFragment() {

    interface OnInputSelected {
        fun sendInputNameSurname(input : String)
    }

    lateinit var onInputSelected: OnInputSelected

    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.dialog_fragment_name_surname, container, false)

        nameEditText = rootView.findViewById(R.id.set_name_dialog_fragment_edit_text)
        surnameEditText = rootView.findViewById(R.id.set_surname_dialog_fragment_edit_text)
        saveButton = rootView.findViewById(R.id.dialog_fragment_name_surname_save_button)

        saveButton.setOnClickListener {
            var input = nameEditText.text.toString().trim() + " " + surnameEditText.text.toString().trim()
            input = input.trim()

            if (input=="") {
                dialog?.dismiss()
            } else {
                onInputSelected.sendInputNameSurname(input)
            }

            dialog?.dismiss()
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            onInputSelected = parentFragment as OnInputSelected
        } catch (e: ClassCastException) {

        }
    }

}