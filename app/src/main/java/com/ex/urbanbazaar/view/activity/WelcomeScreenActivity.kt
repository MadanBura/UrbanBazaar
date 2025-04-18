package com.ex.urbanbazaar.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ex.urbanbazaar.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            splashScreenView.view.animate()
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
        val isFirstTime = tokenManager.getAppFlagDetails()

        if (isFirstTime) {
            tokenManager.setFirstTimeFlag(false)
            startActivity(Intent(this, WelcomeActivity::class.java))
        } else {
            val accessToken = tokenManager.getAccessToken()
            if (accessToken != null) {
                startActivity(Intent(this, DashboardActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        finish()
    }
}
