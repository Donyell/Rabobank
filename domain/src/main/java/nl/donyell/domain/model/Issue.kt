package nl.donyell.domain.model

import java.time.LocalDateTime

data class Issue(
    val firstName: String,
    val surName: String,
    val issueCount: Int,
    val dateOfBirth: LocalDateTime,
    val avatarUrl: String
)
