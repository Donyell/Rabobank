package nl.donyell.domain

import nl.donyell.domain.model.Issue
import javax.inject.Inject

class GetIssuesUseCase @Inject constructor(
    private val issueRepository: IssueRepository
) {

    suspend operator fun invoke(): Result<List<Issue>> {
        return issueRepository.getIssues()
    }
}