package com.assignment.freeletics.data.network.repos.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos_table")
data class RepoEntity(
    @PrimaryKey
    val id : Int,
    val name: String,
    val stargazers: Int,
    val isBookmarked: Boolean
)