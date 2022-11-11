package nl.donyell.data

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import nl.donyell.domain.model.Issue
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response
import java.io.File
import java.io.InputStream
import java.time.LocalDateTime

@ExperimentalCoroutinesApi
@ExtendWith(MockKExtension::class)
internal class IssueRepositoryImplTest {

    @RelaxedMockK
    lateinit var fileManager: FileManager

    @RelaxedMockK
    lateinit var issuesApi: IssueApi

    @InjectMockKs
    lateinit var issueRepositoryImpl: IssueRepositoryImpl

    @Test
    fun `When fetching the issues succeeds, return the issues`() {
        runTest {
            // Given
            mockkStatic(File::toIssues)

            val mockResponseBody = mockk<ResponseBody>()
            val mockResponse = Response.success(mockResponseBody)

            coEvery { issuesApi.downloadCsv() } returns mockResponse
            coEvery { mockResponseBody.byteStream() } returns InputStream.nullInputStream()
            coEvery { fileManager.save(any(), any()) } returns true

            val dateOfBirth = LocalDateTime.of(2022, 1, 15, 0, 0)

            coEvery { any<File>().toIssues() } returns listOf(
                Issue("a", "b", 4, dateOfBirth, "www.avatar.com")
            )

            // When
            val result = issueRepositoryImpl.getIssues()

            // Then
            val issues = listOf(
                Issue("a", "b", 4, dateOfBirth, "www.avatar.com")
            )
            val expectedResult = Result.success(issues)
            assertThat(result).isEqualTo(expectedResult)
        }
    }

    @Test
    fun `When fetching the issues fails, return an error`() {
        runTest {
            // Given
            val errorResponse: Response<ResponseBody> = Response.error(
                400, "".toResponseBody("application/json".toMediaTypeOrNull())
            )

            coEvery { issuesApi.downloadCsv() } returns errorResponse

            // When
            val result = issueRepositoryImpl.getIssues()

            // Then
            assertThat(result.isFailure).isTrue()
        }
    }
}