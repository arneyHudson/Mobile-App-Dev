package edu.msoe.arneyh.lab5.entities

import java.util.UUID

data class ClassDetail (
    val id: UUID,
    val courseID: String,
    val description: String,
    val credits: Int,
)
