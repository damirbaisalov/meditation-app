package com.example.meditation.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.meditation.R
import com.example.meditation.domain.models.SaveUserNameParam
import com.example.meditation.domain.models.UserName
import com.example.meditation.domain.usecase.GetUserNameUseCase
import com.example.meditation.domain.usecase.SaveUserNameUseCase

class MainActivity : AppCompatActivity() {

    private lateinit var dataTextView: TextView
    private lateinit var dataEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var receiveButton: Button

    private val getUserNameUseCase = GetUserNameUseCase()
    private val saveUserNameUseCase = SaveUserNameUseCase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataTextView = findViewById(R.id.get_username_text_view)
        sendButton = findViewById(R.id.save_username_button)
        dataEditText = findViewById(R.id.save_username_edit_text)
        receiveButton = findViewById(R.id.get_username_button)

        sendButton.setOnClickListener {
            val text = dataEditText.text.toString()
            val params = SaveUserNameParam(name = text)
            val result: Boolean = saveUserNameUseCase.execute(param = params)
            dataTextView.text = "Save result = $result"
        }

        receiveButton.setOnClickListener {
            val userName: UserName = getUserNameUseCase.execute()
            dataTextView.text= "${userName.firstName} ${userName.lastName}"
        }
    }
}