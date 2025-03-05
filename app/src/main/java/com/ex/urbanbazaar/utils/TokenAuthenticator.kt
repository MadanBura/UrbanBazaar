package com.ex.urbanbazaar.utils

import android.util.Log
import com.ex.urbanbazaar.dataSource.remote.UrbanApiService
import com.ex.urbanbazaar.model.request.AccessTokenRequest
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    private val apiServiceProvider: Provider<UrbanApiService>
) : Authenticator {

    //Provider -> Provides instances of T. Typically implemented by an injector

    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = tokenManager.getRefreshToken() ?: return null

        synchronized(this) {
            val newAccessToken = getNewAccessToken(refreshToken)
            return if (!newAccessToken.isNullOrBlank()) {
                tokenManager.saveTokens(newAccessToken, refreshToken)
                response.request.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()
            } else {
                tokenManager.clearTokens()
                null
            }
        }
    }

    private fun getNewAccessToken(refreshToken: String): String? {
        return try {
            val response = apiServiceProvider.get().refreshToken(AccessTokenRequest(refreshToken))  // ✅ Uses Provider
            if (response.isSuccessful) response.body()?.access_token else null
        } catch (e: Exception) {
            Log.e("TokenAuthenticator", "Token refresh failed: ${e.message}")
            null
        }
    }
}
