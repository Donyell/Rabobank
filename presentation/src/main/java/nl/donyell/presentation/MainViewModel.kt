package nl.donyell.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.donyell.domain.GetIssuesUseCase
import nl.donyell.domain.model.Issue
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getIssuesUseCase: GetIssuesUseCase
): ViewModel() {

    private val _mainUiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> by lazy {
        _mainUiState.apply {
            observeIssues()
        }
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler{ _, throwable ->
        Log.e("MainViewModel", "coroutineExceptionHandler throwable: $throwable")
    }

    private fun observeIssues() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            getIssues()
        }
    }

    private suspend fun getIssues() {
        _mainUiState.update {
            it.copy(showLoader = true)
        }

        getIssuesUseCase()
            .onSuccess (::onGetIssuesSuccess)
            .onFailure (::onGetIssuesFailure)
    }

    private fun onGetIssuesSuccess(issues: List<Issue>) {
        val uiModelIssues = issues.map { it.toIssueUiModel() }

        _mainUiState.update {
            it.copy(
                issues = uiModelIssues,
                showLoader = false
            )
        }
    }

    private fun onGetIssuesFailure(throwable: Throwable) {
        Log.e("MainViewModel", "Error retrieving issues", throwable)
        _mainUiState.update {
            it.copy(
                showLoader = false
            )
        }
    }
}