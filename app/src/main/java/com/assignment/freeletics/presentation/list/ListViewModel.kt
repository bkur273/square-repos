package com.assignment.freeletics.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.freeletics.data.network.repos.RepoRepository
import com.assignment.freeletics.domain.Repo
import com.assignment.freeletics.presentation.shared.navigation.NavigationCommand
import com.assignment.freeletics.presentation.shared.state.MutableViewStateObserver
import com.assignment.freeletics.presentation.shared.state.ViewStateObserver
import com.assignment.freeletics.shared.coroutines.AppDispatchers
import com.assignment.freeletics.shared.coroutines.ErrorContextHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val observer: MutableViewStateObserver,
    private val repoRepository: RepoRepository,
    private val appDispatchers: AppDispatchers,
    private val errorContextHandler: ErrorContextHandler
) : ViewModel(), ViewStateObserver by observer {

    val listItems = MutableLiveData<List<UiModel>>()

    init {
        fetchRepos()
    }

    private fun fetchRepos() {
        repoRepository.repos().map { list ->
            list.map { UiModel(it, onClick = { onRepoItemClicked(it) }) }
        }.onStart { observer.setLoadingState(true) }
            .catch {
                errorContextHandler.handle(it) {
                    showDialog = true
                    onDialogDismiss = { fetchRepos() }
                }
            }.onEach {
                observer.setLoadingState(false)
                listItems.postValue(it)
            }.flowOn(appDispatchers.io)
            .launchIn(viewModelScope)
    }

    private fun onRepoItemClicked(repo: Repo) {
        observer.setNavigationCommand(
            NavigationCommand.To(
                ListFragmentDirections.actionListFragmentToDetailsFragment(
                    repo.id
                )
            )
        )
    }

    data class UiModel(
        val data: Repo,
        val onClick: () -> Unit
    )

}