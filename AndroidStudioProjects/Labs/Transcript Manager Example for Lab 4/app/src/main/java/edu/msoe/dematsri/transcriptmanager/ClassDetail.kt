package edu.msoe.dematsri.transcriptmanager

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class ClassDetail (
    @PrimaryKey val id: UUID,
    val courseID: String,
    val credits: Int,
    val grade: String,
    val startDate: Date
)
