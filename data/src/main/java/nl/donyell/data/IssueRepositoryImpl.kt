package nl.donyell.data

import nl.donyell.domain.model.Issue
import nl.donyell.domain.IssueRepository
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject


class IssueRepositoryImpl @Inject constructor(
    private val fileManager: FileManager,
    private val issueApi: IssueApi
) : IssueRepository {

    override suspend fun getIssues(): Result<List<Issue>> {
        val response = issueApi.downloadCsv()
        return if (response.isSuccessful) {
            onDownloadCsvSuccess(response)
        } else {
            onDownloadCsvFailed(response)
        }
    }

    private fun onDownloadCsvSuccess(response: Response<ResponseBody>): Result<List<Issue>> {
        return try {
            val byteStream: InputStream =
                response.body()?.byteStream() ?: throw IOException("Bytestream not available")
            fileManager.save(byteStream, FILE_NAME)
            val csvFile = fileManager.get(FILE_NAME)
            Result.success(csvFile.toIssues())
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    private fun onDownloadCsvFailed(response: Response<ResponseBody>): Result<List<Issue>> {
        return Result.failure(HttpException(response))
    }

    companion object {
        private const val FILE_NAME = "issues.csv"
    }
}