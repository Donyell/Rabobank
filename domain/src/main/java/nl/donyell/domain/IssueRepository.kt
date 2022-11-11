package nl.donyell.domain

import nl.donyell.domain.model.Issue

interface IssueRepository {

    suspend fun getIssues(): Result<List<Issue>>
}