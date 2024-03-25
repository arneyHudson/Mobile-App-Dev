package edu.msoe.arneyh.lab5.entities

import java.util.UUID

data class Transcript (
    val id: UUID,
    val student: UUID,
    val term: UUID,
    val course: UUID,
    val grade: String
)
