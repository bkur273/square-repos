package com.assignment.freeletics.di

import com.assignment.freeletics.BuildConfig
import com.assignment.freeletics.data.network.repos.RepoService
import com.assignment.freeletics.data.network.shared.NetworkCallAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofitInstance(
        okHttpClient: OkHttpClient,
        converter: Converter.Factory,
    ): Retrofit {
        val builder = Retrofit.Builder()
        builder.client(okHttpClient)
        builder.baseUrl(BuildConfig.BASE_URL)
        builder.addConverterFactory(converter)
        builder.addCallAdapterFactory(NetworkCallAdapterFactory())
        return builder.build()
    }

    @Provides
    fun providesAuthorizedOkHttpInstance() = OkHttpClient().newBuilder().run {

        readTimeout(60, TimeUnit.SECONDS)
        connectTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)

        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        build()
    }


    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun providesJsonConverter(json: Json): Converter.Factory =
        json.asConverterFactory("application/json".toMediaTypeOrNull()!!)

    @Provides
    @Singleton
    fun providesJson() = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun providesRepoService(retrofit: Retrofit) = retrofit.create<RepoService>()

}