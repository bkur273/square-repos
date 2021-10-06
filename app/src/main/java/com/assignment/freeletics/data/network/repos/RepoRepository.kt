package com.assignment.freeletics.data.network.repos

import com.assignment.freeletics.data.network.repos.models.RepoDTO
import com.assignment.freeletics.data.network.repos.models.RepoEntity
import com.assignment.freeletics.data.network.shared.result
import com.assignment.freeletics.domain.Repo
import com.assignment.freeletics.domain.RepoList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface RepoRepository {

    fun repos(): Flow<Iterable<Repo>>
    fun repo(id: Int): Flow<Repo>
    suspend fun bookmark(id: Int, value: Boolean)

    class Square(
        private val service: RepoService,
        private val dao: RepoDao
    ) : RepoRepository {

        override fun repos(): Flow<Iterable<Repo>> {
            return dao.getAll().onStart {
                val reposDto = service.getSquareRepos().result()
                val entities = reposDto.map { it.toEntity() }
                dao.insert(entities)
            }.map { list -> RepoList(list.map { it.toDomainModel() }) }
        }

        override fun repo(id: Int): Flow<Repo> {
            return dao.get(id).map { it.toDomainModel() }
        }

        override suspend fun bookmark(id: Int, value: Boolean) {
            dao.bookmark(id, value)
        }

        private suspend fun RepoDTO.toEntity() = RepoEntity(
            id = id,
            name = name,
            stargazers = stargazers,
            isBookmarked = dao.get(id).firstOrNull()?.isBookmarked ?: false
        )

        private fun RepoEntity.toDomainModel() = Repo(
            id = id,
            name = name,
            stargazers = stargazers,
            isBookmarked = isBookmarked
        )

    }

}