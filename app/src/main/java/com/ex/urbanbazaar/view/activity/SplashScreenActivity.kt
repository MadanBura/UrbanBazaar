package com.ex.urbanbazaar.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ex.urbanbazaar.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()


        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val view = splashScreenView.view

            view.animate()
                .alpha(0f)
                .setDuration(3000)
                .withEndAction {
                    splashScreenView.remove()
                    navigateToNextScreen()
                }
                .start()
        }
    }

    private fun navigateToNextScreen() {
//        val accessToken = PreferenceManager.getAccessToken(this)
//        val isLoggedIn = !accessToken.isNullOrBlank()

        val isLoggedIn = false
        val nextActivity = if (isLoggedIn) {
            DashboardActivity::class.java
        } else {
            LoginActivity::class.java
        }

        startActivity(Intent(this, nextActivity))
        finish()
    }


}