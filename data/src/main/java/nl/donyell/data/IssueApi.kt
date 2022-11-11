package nl.donyell.data

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming

interface IssueApi {

    @Streaming
    @GET("RabobankDev/AssignmentCSV/main/issues.csv")
    suspend fun downloadCsv(): Response<ResponseBody>
}