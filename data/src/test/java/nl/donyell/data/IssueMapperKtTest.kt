package nl.donyell.data

import com.google.common.truth.Truth.assertThat
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import nl.donyell.domain.model.Issue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.io.File
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class IssueMapperKtTest {

    @Test
    fun `When there is an CSV file with issues, map it to issues`() {
        // Given
        val path = "src/test/resources/"
        val fileName = "test_issues.csv"
        val csvFile = File(path + fileName)

        // When
        val result = csvFile.toIssues()

        // Then
        val expectedResult = listOf(
            Issue(
                firstName = "Peter",
                surName = "Smit",
                issueCount = 5,
                dateOfBirth = LocalDateTime.of(1978, 1, 2, 0, 0),
                avatarUrl = "https://api.multiavatar.com/2cdf5db9b4dee297b7.png"
            ),
            Issue(
                firstName = "Milou",
                surName = "de Vries",
                issueCount = 7,
                dateOfBirth = LocalDateTime.of(1950, 11, 12, 0, 0),
                avatarUrl = "https://api.multiavatar.com/b9339cb9e7a833cd5e.png"
            ),
            Issue(
                firstName = "Mieke",
                surName = "Pater",
                issueCount = 1,
                dateOfBirth = LocalDateTime.of(2001, 4, 20, 0, 0),
                avatarUrl = "https://api.multiavatar.com/2672c49d6099f87274.png"
            )

        )
        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `When there is a file without the proper headers, throw an exception`() {
        // Given
        val path = "src/test/resources/"
        val fileName = "test_issues_with_invalid_header.csv"
        val csvFile = File(path + fileName)


        // Then
        assertThrows<Exception> {
            // When
            csvFile.toIssues()
        }
    }
}