package com.assignment.freeletics.data.network.repos.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoDTO(
    val id : Int,
    val name: String,
    @SerialName("stargazers_count")
    val stargazers: Int,
)