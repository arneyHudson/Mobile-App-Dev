package edu.msoe.arneyh.lab5.entities

import java.util.UUID

data class Student (
    val id: UUID,
    val firstName: String,
    val lastName: String,
    val firstTermOfStudy: UUID
)
