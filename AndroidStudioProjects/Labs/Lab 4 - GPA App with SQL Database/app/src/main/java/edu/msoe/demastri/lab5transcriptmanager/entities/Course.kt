package edu.msoe.demastri.lab5transcriptmanager.entities

data class Course(val name: String, val credits: String, val grade: String) {
    val gradePoints: Double
        get() {
            return when (grade.trim().toUpperCase()) {
                "A" -> 4.0
                "AB" -> 3.5
                "B" -> 3.0
                "BC" -> 2.5
                "C" -> 2.0
                "CD" -> 1.5
                "D" -> 1.0
                "F" -> 0.0
                "P" -> 4.0
                else -> 0.0 // Default for unknown string grades
            }
        }
}