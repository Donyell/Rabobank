package nl.donyell.data

import nl.donyell.domain.model.Issue
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
private const val HEADER_INDEX = 0

fun File.toIssues(): List<Issue> {
    val issues = mutableListOf<Issue>()
    val bufferedReader = inputStream().bufferedReader()
    bufferedReader
        .readLines()
        .forEachIndexed { index, line ->
            if (index == HEADER_INDEX) {
                validateHeader(line)
            } else {
                addIssue(line, issues)
            }
        }
    return issues
}

fun validateHeader(header: String) {
    val expectedHeader = """"First name","Sur name","Issue count","Date of birth","avatar""""
    if (header != expectedHeader) {
        throw Exception("The header is not equal to: $expectedHeader")
    }
}

private fun addIssue(line: String, issues: MutableList<Issue>) {
    val (firstName, surName, issueCount, birthDate, avatarUrl) = line.split(",")

    val dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN)
    val dateOfBirth = LocalDateTime.parse(birthDate.removeSurrounding("\""), dateFormatter)
    val issue = Issue(
        firstName.removeSurrounding("\""),
        surName.removeSurrounding("\""),
        issueCount.toInt(),
        dateOfBirth,
        avatarUrl.removeSurrounding("\"")
    )
    issues.add(issue)
}



