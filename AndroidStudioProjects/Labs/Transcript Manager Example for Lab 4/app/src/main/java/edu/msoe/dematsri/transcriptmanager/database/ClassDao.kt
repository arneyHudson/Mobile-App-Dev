package edu.msoe.dematsri.transcriptmanager.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.msoe.dematsri.transcriptmanager.ClassDetail
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ClassDao {
    @Query("SELECT * FROM classDetail")
    fun getClassDetails(): Flow<List<ClassDetail>>

    @Query("SELECT * FROM classDetail WHERE id=(:id)")
    fun getClassDetail(id: UUID): ClassDetail

    @Query("Delete FROM classDetail")
    fun deleteClassDetails()

    @Query("DELETE FROM classDetail WHERE id=(:id)")
    fun deleteThisClass(id: UUID)

    @Insert
    fun addClassDetail(classDetail: ClassDetail)

    @Update
    fun updateClassDetail(classDetail: ClassDetail)
}