package nl.donyell.presentation

import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import nl.donyell.domain.GetIssuesUseCase
import nl.donyell.domain.model.Issue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneOffset

@OptIn(ExperimentalCoroutinesApi::class)
internal class MainViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @RelaxedMockK
    lateinit var getIssuesUseCase: GetIssuesUseCase

    @InjectMockKs
    lateinit var mainViewModel: MainViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `When the UI state gets updated, emit the new UI state`() {
        runTest {
            // Given
            val birthDateEpochInSeconds = 315576000L //UTC: Tuesday, 1 January 1980 12:00:00
            val issue = Issue(
                "Jan",
                "Janssen",
                4,
                LocalDateTime.ofEpochSecond(birthDateEpochInSeconds, 0, ZoneOffset.UTC),
                "www.nu.nl"
            )
            val issues = listOf(issue)
            val issuesResult = Result.success(issues)

            coEvery { getIssuesUseCase() } returns issuesResult

            // When
            val result = mainViewModel.uiState.take(2).last()

            // Then
            val expectedIssue = IssueUiModel(
                fullName = "Jan Janssen",
                dateOfBirth = "01-01-1980",
                avatarUrl = "www.nu.nl",
                issueCount = 4
            )
            val expectedIssues = listOf(expectedIssue)

            val expectedResult = MainUiState(
                issues = expectedIssues,
                showLoader = false
            )
            assertThat(result).isEqualTo(expectedResult)
        }
    }
}