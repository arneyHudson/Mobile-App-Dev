package edu.msoe.dematsri.transcriptmanager

import java.util.UUID

data class StudentRecord (
    val id: UUID,
    val term: String,
    val courses: ArrayList<ClassDetail>
)