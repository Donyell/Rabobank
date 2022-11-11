package nl.donyell.rabobank

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import nl.donyell.domain.model.Issue
import nl.donyell.presentation.MainUiState
import nl.donyell.presentation.MainViewModel
import nl.donyell.presentation.toIssueUiModel
import nl.donyell.rabobank.ui.theme.RabobankTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime
import java.time.ZoneOffset

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun whenThereAreNoIssues_thenShowAnEmptyList() {
        // Given
        val mockViewModel = mockk<MainViewModel>()

        every { mockViewModel.uiState } returns flowOf(
            MainUiState(emptyList(), false)
        )

        // When
        composeTestRule.activity.setContent {
            RabobankTheme {
                IssuesListScreen(mockViewModel)
            }
        }

        // Then
        composeTestRule.onNodeWithTag(MainActivity.ISSUES_LIST_TAG)
            .onChildren()
            .assertCountEquals(0)
    }

    @Test
    fun whenTheIssuesAreLoading_thenShowALoader() {
        // Given
        val mockViewModel = mockk<MainViewModel>()

        every { mockViewModel.uiState } returns flowOf(
            MainUiState(emptyList(), true)
        )

        // When
        composeTestRule.activity.setContent {
            RabobankTheme {
                IssuesListScreen(mockViewModel)
            }
        }

        // Then
        composeTestRule.onNodeWithTag(MainActivity.LOADER_TAG).assertIsDisplayed()
    }

    @Test
    fun whenThereAreIssues_thenShowTheIssues() {
        // Given
        val birthDateEpochInSeconds = 315576000L //UTC: Tuesday, 1 January 1980 12:00:00
        val issue1 = Issue(
            "Jan",
            "Janssen",
            0,
            LocalDateTime.ofEpochSecond(birthDateEpochInSeconds, 0, ZoneOffset.UTC),
            "www.nos.nl"
        )

        val issue2 = Issue(
            "Peter",
            "B",
            1,
            LocalDateTime.ofEpochSecond(birthDateEpochInSeconds, 0, ZoneOffset.UTC),
            "www.nu.nl"
        )

        val issue3 = Issue(
            "Ali",
            "El Khayam",
            17,
            LocalDateTime.ofEpochSecond(birthDateEpochInSeconds, 0, ZoneOffset.UTC),
            "www.twee.nl"
        )

        val issues = listOf(issue1, issue2, issue3)
        val issueUiModel = issues.map { it.toIssueUiModel() }

        val mockViewModel = mockk<MainViewModel>()
        every { mockViewModel.uiState } returns flowOf(
            MainUiState(issueUiModel, true)
        )

        // When
        composeTestRule.activity.setContent {
            RabobankTheme {
                IssuesListScreen(mockViewModel)
            }
        }

        // Then
        composeTestRule.onNodeWithTag(MainActivity.ISSUES_LIST_TAG)
            .onChildren()
            .assertCountEquals(3)

        composeTestRule.onNodeWithTag(MainActivity.ISSUES_LIST_TAG)
            .onChildAt(0)
            .onChildAt(0)
            .assertTextContains("Jan Janssen")

        composeTestRule.onNodeWithTag(MainActivity.ISSUES_LIST_TAG)
            .onChildAt(1)
            .onChildAt(0)
            .assertTextContains("Peter B")

        composeTestRule.onNodeWithTag(MainActivity.ISSUES_LIST_TAG)
            .onChildAt(2)
            .onChildAt(0)
            .assertTextContains("Ali El Khayam")
    }
}