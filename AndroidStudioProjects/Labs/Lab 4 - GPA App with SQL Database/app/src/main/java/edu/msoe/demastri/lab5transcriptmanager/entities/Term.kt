package edu.msoe.demastri.lab5transcriptmanager.entities

import java.util.Date
import java.util.UUID

data class Term (
    val id: UUID,
    val name: String,
    val firstClassDay: Date,
)
