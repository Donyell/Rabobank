package nl.donyell.rabobank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint
import nl.donyell.presentation.IssueUiModel
import nl.donyell.presentation.MainUiState
import nl.donyell.presentation.MainViewModel
import nl.donyell.rabobank.MainActivity.Companion.LOADER_TAG
import nl.donyell.rabobank.ui.theme.RabobankTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RabobankTheme {
                IssuesListScreen()
            }
        }
    }

    companion object {
        const val ISSUES_LIST_TAG = "issues_list_tag"
        const val LOADER_TAG = "loader_tag"
    }
}


@Composable
fun IssuesListScreen(mainViewModel: MainViewModel = viewModel()) {
    val uiState = mainViewModel.uiState.collectAsState(initial = MainUiState())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        IssuesList(uiState.value)
        if (uiState.value.showLoader) {
            IssueLoader()
        }
    }
}

@Composable
private fun IssuesList(uiState: MainUiState) {
    LazyColumn(modifier = Modifier.testTag(MainActivity.ISSUES_LIST_TAG)) {
        items(uiState.issues) { issue ->
            IssueRow(issue = issue)
        }
    }
}


@Composable
fun IssueRow(issue: IssueUiModel) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IssueAvatar(issue)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(issue.fullName)
                Text(issue.dateOfBirth)
                Text(text = stringResource(R.string.issue_count, issue.issueCount))
            }
        }
    }
}


@Composable
private fun IssueAvatar(issue: IssueUiModel) {
    AsyncImage(
        modifier = Modifier
            .size(48.dp),
        model = issue.avatarUrl,
        contentDescription = null
    )
}

@Composable
private fun IssueLoader() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .testTag(LOADER_TAG)
    ) {
        CircularProgressIndicator()
    }

}



