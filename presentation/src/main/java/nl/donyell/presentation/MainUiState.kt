package nl.donyell.presentation

data class MainUiState(
    val issues: List<IssueUiModel> = emptyList(),
    val showLoader: Boolean = true
)
