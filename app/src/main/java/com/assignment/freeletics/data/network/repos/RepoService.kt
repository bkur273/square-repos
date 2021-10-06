package com.assignment.freeletics.data.network.repos

import com.assignment.freeletics.data.network.repos.models.RepoDTO
import com.assignment.freeletics.data.network.shared.NetworkResponse
import retrofit2.http.GET

interface RepoService {

    @GET("square/repos")
    suspend fun getSquareRepos() : NetworkResponse<List<RepoDTO>>

}