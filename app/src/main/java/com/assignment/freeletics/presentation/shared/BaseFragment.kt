package com.assignment.freeletics.presentation.shared

import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.assignment.freeletics.R
import com.assignment.freeletics.presentation.shared.state.ViewStateObserver
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class BaseFragment<T : ViewStateObserver>(layoutRes: Int) : Fragment(layoutRes) {

    abstract val viewModel: T
    private val progressBar by lazy { ProgressDialog(requireActivity()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.navigationState()
                        .collect {
                            it.navigate(findNavController())
                        }
                }

                launch {
                    viewModel.errorText().collect { errorDialogData ->
                        AlertDialog.Builder(requireContext())
                            .setTitle(R.string.something_went_wrong)
                            .setMessage(errorDialogData.throwable.message)
                            .setOnDismissListener { errorDialogData.onDismiss() }
                            .show()
                    }
                }

                launch {
                    viewModel.showLoading().collect {
                        if (it)
                            showProgress()
                        else
                            hideProgress()
                    }
                }
            }
        }
    }

    protected open fun showProgress() {
        progressBar.setMessage("loading")
        progressBar.show()
    }

    protected open fun hideProgress() {
        progressBar.hide()
    }

}