package com.assignment.freeletics.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.freeletics.R
import com.assignment.freeletics.data.network.repos.RepoRepository
import com.assignment.freeletics.domain.Repo
import com.assignment.freeletics.presentation.shared.state.MutableViewStateObserver
import com.assignment.freeletics.presentation.shared.state.ViewStateObserver
import com.assignment.freeletics.shared.coroutines.AppDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val observer: MutableViewStateObserver,
    private val repoRepository: RepoRepository,
    private val appDispatchers: AppDispatchers,
    savedStateHandle: SavedStateHandle
) : ViewModel(), ViewStateObserver by observer {

    private val id: Int =
        savedStateHandle["id"] ?: error("Could not find repo id in savedStateHandle")
    val details = MutableLiveData<UiModel>()

    init {
        viewModelScope.launch(appDispatchers.io) {
            repoRepository.repo(id).collect {
                details.postValue(
                    UiModel(
                        data = it,
                        bookmarkImageRes = if (it.isBookmarked)
                            R.drawable.ic_baseline_bookmark_24
                        else
                            R.drawable.ic_baseline_bookmark_border_24
                    ) { bookmark(it) }
                )
            }
        }
    }

    private fun bookmark(repo: Repo) {
        viewModelScope.launch(appDispatchers.io) {
            repoRepository.bookmark(repo.id, !repo.isBookmarked)
        }
    }

    data class UiModel(
        val data: Repo,
        val bookmarkImageRes: Int,
        val onClick: () -> Unit
    )

}