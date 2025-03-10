package com.ex.urbanbazaar.di

import android.content.Context
import com.ex.urbanbazaar.dataSource.remote.UrbanApiService
import com.ex.urbanbazaar.repository.ProductRepository
import com.ex.urbanbazaar.repository.UserRepository
import com.ex.urbanbazaar.repositoryImpl.ProductRepositoryImpl
import com.ex.urbanbazaar.repositoryImpl.UserRepositoryImpl
import com.ex.urbanbazaar.utils.AuthInterceptor
import com.ex.urbanbazaar.utils.TokenAuthenticator
import com.ex.urbanbazaar.utils.TokenManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        tokenManager: TokenManager,
        tokenAuthenticator: TokenAuthenticator,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(tokenManager))
            .authenticator(tokenAuthenticator)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.escuelajs.co/api/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): UrbanApiService {
        return retrofit.create(UrbanApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideUserRepository(apiService: UrbanApiService, tokenManager: TokenManager):UserRepository{
        return UserRepositoryImpl(apiService, tokenManager)
    }


    @Provides
    @Singleton
    fun provideProductRepository(apiService: UrbanApiService):ProductRepository{
        return ProductRepositoryImpl(apiService)
    }

}
