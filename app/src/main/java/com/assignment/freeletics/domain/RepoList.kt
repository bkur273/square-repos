package com.assignment.freeletics.domain

class RepoList(private val list: Iterable<Repo>) : Iterable<Repo> {
    override fun iterator(): Iterator<Repo> {
        return list.sortedByDescending { it.isBookmarked }.iterator()
    }
}