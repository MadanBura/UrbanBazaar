package com.ex.urbanbazaar.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ex.urbanbazaar.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()// ensures that all activities in the task are finished, exiting the app completely.
        moveTaskToBack(true) // Moves the app to the background
    }

}