package com.assignment.freeletics.data.network.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.assignment.freeletics.data.network.repos.RepoDao
import com.assignment.freeletics.data.network.repos.models.RepoEntity

@Database(entities = [RepoEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract val repoDao: RepoDao
}