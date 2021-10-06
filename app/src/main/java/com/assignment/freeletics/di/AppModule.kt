package com.assignment.freeletics.di

import android.content.Context
import androidx.room.Room
import com.assignment.freeletics.data.network.local.AppDatabase
import com.assignment.freeletics.data.network.repos.RepoRepository
import com.assignment.freeletics.data.network.repos.RepoService
import com.assignment.freeletics.presentation.shared.date.DateFormatter
import com.assignment.freeletics.shared.coroutines.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesAppDispatchers() = AppDispatchers(
        main = Dispatchers.Main,
        io = Dispatchers.IO,
        background = Dispatchers.Default
    )


    @Provides
    @Singleton
    fun providesDateTimeFormatter(
        @ApplicationContext context: Context
    ): DateFormatter = DateFormatter.Impl(context)

    @Provides
    @Singleton
    fun providesDataBase(@ApplicationContext applicationContext: Context): AppDatabase = Room.databaseBuilder(
        applicationContext,
        AppDatabase::class.java, "square-repos"
    ).build()

    @Provides
    @Singleton
    fun providesRepoRepository(
        appDatabase: AppDatabase,
        service: RepoService
    ): RepoRepository = RepoRepository.Square(service, appDatabase.repoDao)

}