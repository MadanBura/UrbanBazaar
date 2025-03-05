package com.ex.urbanbazaar.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(@ApplicationContext context: Context) {
    private val prefs = context.getSharedPreferences("urbanBazaarCredentials", Context.MODE_PRIVATE)

    fun saveTokens(accessToken: String, refreshToken: String) {
        prefs.edit()
            .putString("ACCESS_TOKEN", accessToken)
            .putString("REFRESH_TOKEN", refreshToken)
            .apply()
    }

    fun getAccessToken(): String? = prefs.getString("ACCESS_TOKEN", null)
    fun getRefreshToken(): String? = prefs.getString("REFRESH_TOKEN", null)

    fun getAppFlagDetails(): Boolean = prefs.getBoolean("IS_FIRST_TIME", true)

    fun setFirstTimeFlag(isFirstTime: Boolean) {
        prefs.edit().putBoolean("IS_FIRST_TIME", isFirstTime).apply()
    }

    fun clearTokens() {
        prefs.edit().clear().apply()
    }
}
