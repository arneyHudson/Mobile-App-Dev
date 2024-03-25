package edu.msoe.demastri.lab5transcriptmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.msoe.demastri.lab5transcriptmanager.entities.Term
import kotlinx.coroutines.flow.Flow
import java.util.UUID
@Dao
interface TermDao {
    @Query("SELECT * FROM term")
    fun getTerms(): Flow<List<Term>>

    @Query("SELECT * FROM term WHERE id=(:id)")
    fun getTerm(id: UUID): Term

    @Query("DELETE FROM term")
    fun deleteTerms()

    @Query("DELETE FROM term WHERE id=(:id)")
    fun deleteThisTerm(id: UUID)

    @Insert
    suspend fun insertTerm(term: Term)

    @Update
    suspend fun updateTerm(term: Term)

    @Delete
    suspend fun deleteTerm(term: Term)
}
