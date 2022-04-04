package com.example.meditation.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.meditation.R
import com.example.meditation.data.repository.UserRepositoryImpl
import com.example.meditation.data.storage.sharedprefs.SharedPrefUserStorage
import com.example.meditation.domain.models.SaveUserNameParam
import com.example.meditation.domain.models.UserName
import com.example.meditation.domain.repository.UserRepository
import com.example.meditation.domain.usecase.GetUserNameUseCase
import com.example.meditation.domain.usecase.SaveUserNameUseCase

class MainActivity : AppCompatActivity() {

    private lateinit var dataTextView: TextView
    private lateinit var dataEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var receiveButton: Button

//    private val sharedPrefUserStorage by lazy(LazyThreadSafetyMode.NONE) { SharedPrefUserStorage(context = applicationContext)}
    private val userRepository by lazy(LazyThreadSafetyMode.NONE) { UserRepositoryImpl(sharedPrefUserStorage = SharedPrefUserStorage(context = applicationContext)) }
    private val getUserNameUseCase by lazy(LazyThreadSafetyMode.NONE) { GetUserNameUseCase(userRepository = userRepository) }
    private val saveUserNameUseCase by lazy(LazyThreadSafetyMode.NONE) { SaveUserNameUseCase(userRepository = userRepository) }

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