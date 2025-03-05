package com.ex.urbanbazaar.utils

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = tokenManager.getAccessToken()
        val request = chain.request().newBuilder()

        accessToken?.let {
            request.addHeader("Authorization", "Bearer $it")
            request.header("Content-Type", "application/json")
        }

        return chain.proceed(request.build())
    }

}
