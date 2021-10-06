package com.assignment.freeletics.data.network.repos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assignment.freeletics.data.network.repos.models.RepoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {

    @Query("SELECT * FROM repos_table")
    fun getAll(): Flow<List<RepoEntity>>

    @Query("SELECT * FROM repos_table WHERE id = :id")
    fun get(id: Int): Flow<RepoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: List<RepoEntity>)

    @Query("UPDATE repos_table SET isBookmarked = :state WHERE id = :id")
    suspend fun bookmark(id: Int, state: Boolean)

}