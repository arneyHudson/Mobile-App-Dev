package edu.msoe.demastri.lab5transcriptmanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.msoe.demastri.lab5transcriptmanager.entities.Transcript
import java.util.UUID
import kotlinx.coroutines.flow.Flow

@Dao
interface TranscriptDao {
    @Query("SELECT * FROM transcript")
    fun getTranscripts(): Flow<List<Transcript>>

    @Query("SELECT * FROM transcript WHERE id=(:id)")
    fun getTranscript(id: UUID): Transcript

    @Query("DELETE FROM transcript")
    fun deleteTranscripts()

    @Query("DELETE FROM transcript WHERE id=(:id)")
    fun deleteThisTranscript(id: UUID)
    @Insert
    suspend fun insertTranscript(transcript: Transcript)

    @Update
    suspend fun updateTranscript(transcript: Transcript)

    @Delete
    suspend fun deleteTranscript(transcript: Transcript)
}
