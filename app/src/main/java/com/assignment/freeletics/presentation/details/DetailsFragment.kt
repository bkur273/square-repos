package com.assignment.freeletics.presentation.details

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.assignment.freeletics.databinding.DetailsFragmentBinding
import com.assignment.freeletics.presentation.shared.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<DetailsViewModel>(R.layout.details_fragment) {
    override val viewModel by viewModels<DetailsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = DetailsFragmentBinding.bind(view)
        binding.root.setContent {
            val details = viewModel.details.observeAsState()
            Column(modifier = Modifier.padding(16.dp)) {
                details.value?.let { RepoDetails(details = it) }
            }
        }
    }

    @Composable
    private fun RepoDetails(details: DetailsViewModel.UiModel) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = details.data.name,
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onPrimary
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            text = getString(R.string.stargazers, details.data.stargazers.toString()),
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onPrimary
        )
        Image(
            painterResource(id = details.bookmarkImageRes),
            getString(R.string.is_bookmarked),
            modifier = Modifier.width(36.dp).height(36.dp).clickable { details.onClick() },
            colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
        )
    }

}