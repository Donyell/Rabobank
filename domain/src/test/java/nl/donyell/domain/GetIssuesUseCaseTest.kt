package nl.donyell.domain

import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.donyell.domain.model.Issue
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime
import java.time.ZoneOffset

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class GetIssuesUseCaseTest {

    @RelaxedMockK
    lateinit var issueRepository: IssueRepository

    @InjectMockKs
    lateinit var getIssuesUseCase: GetIssuesUseCase

    @Test
    fun `Given the issue repository contains issues, when invoked, return the issues from the issue repository`() {
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
            val expectedResult = Result.success(issues)

            coEvery { issueRepository.getIssues() } returns expectedResult

            // When
            val result = getIssuesUseCase()

            // Then
            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `Given the issue repository contains an error, when invoked, return the error from the issue repository`() {
        runTest {
            // Given
            coEvery { issueRepository.getIssues() } returns Result.failure(Exception())

            // When
            val result = getIssuesUseCase()

            // Then
            assertThat(result.isFailure).isTrue()
        }
    }
}