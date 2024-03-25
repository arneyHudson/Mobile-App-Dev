package edu.msoe.demastri.lab5transcriptmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.msoe.demastri.lab5transcriptmanager.entities.Student // Import the relevant entity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface StudentDao {
    @Query("SELECT * FROM student")
    fun getStudents(): Flow<List<Student>>

    @Query("SELECT * FROM student WHERE id=(:id)")
    fun getStudent(id: UUID): Student

    @Query("Delete FROM student")
    fun deleteStudents()

    @Query("DELETE FROM student WHERE id=(:id)")
    fun deleteThisStudent(id: UUID)

    @Insert
    fun addStudent(student: Student)

    @Update
    fun updateStudent(student: Student)
}