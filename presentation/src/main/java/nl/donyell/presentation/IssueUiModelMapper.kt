package nl.donyell.presentation

import nl.donyell.domain.model.Issue
import java.time.format.DateTimeFormatter

private const val BIRTHDAY_FORMAT = "dd-MM-yyyy"

fun Issue.toIssueUiModel(): IssueUiModel{
    val dateFormatter = DateTimeFormatter.ofPattern(BIRTHDAY_FORMAT)
    return IssueUiModel(
        fullName = "${this.firstName} ${this.surName}",
        dateOfBirth = this.dateOfBirth.format(dateFormatter),
        avatarUrl = this.avatarUrl,
        issueCount = this.issueCount
    )
}