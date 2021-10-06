package com.assignment.freeletics.presentation.list

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.assignment.freeletics.R
import com.assignment.freeletics.databinding.ListFragmentBinding
import com.assignment.freeletics.presentation.shared.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@AndroidEntryPoint
class ListFragment : BaseFragment<ListViewModel>(R.layout.list_fragment) {
    override val viewModel: ListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = ListFragmentBinding.bind(view)

        binding.listComposable.setContent {
            val listState = viewModel.listItems.observeAsState()
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                items(listState.value ?: emptyList()) { item ->
                    RepoListItem(item)
                }
            }
        }
    }

    @Composable
    private fun RepoListItem(item: ListViewModel.UiModel) {
        Card(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .fillMaxWidth()
                .requiredHeightIn(min = 100.dp, max = 250.dp)
                .clickable { item.onClick() },
            elevation = 4.dp
        ) {

            Column {
                if (item.data.isBookmarked)
                    Image(
                        painterResource(id = R.drawable.ic_baseline_bookmark_24),
                        getString(R.string.is_bookmarked),
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                        modifier = Modifier.padding(8.dp)
                    )
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    text = item.data.name,
                    style = MaterialTheme.typography.subtitle1,
                )
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    text = getString(R.string.stargazers, item.data.stargazers.toString()),
                    style = MaterialTheme.typography.body2,
                )
            }
        }
    }
}