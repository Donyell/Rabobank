package nl.donyell.presentation

import com.google.common.truth.Truth.assertThat
import nl.donyell.domain.model.Issue
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneOffset

internal class IssueUiModelMapperKtTest {

    @Test
    fun `When there is an issue, map it to an issue UI model`() {
        // Given
        val birthDateEpochInSeconds = 315576000L //UTC: Tuesday, 1 January 1980 12:00:00

        val issue = Issue(
            "Leonie",
            "Lammer",
            0,
            LocalDateTime.ofEpochSecond(birthDateEpochInSeconds, 0, ZoneOffset.UTC),
            "www.nos.nl"
        )

        // When
        val result = issue.toIssueUiModel()

        // Then
        val expectedResult = IssueUiModel(
            fullName = "Leonie Lammer",
            dateOfBirth = "01-01-1980",
            avatarUrl = "www.nos.nl",
            issueCount = 0
        )
        assertThat(result).isEqualTo(expectedResult)
    }
}