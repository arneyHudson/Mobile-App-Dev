package edu.msoe.demastri.lab5transcriptmanager

import edu.msoe.demastri.lab5transcriptmanager.entities.ClassDetail
import java.util.UUID

data class StudentRecord (
    val id: UUID,
    val term: String,
    val courses: ArrayList<ClassDetail>
)