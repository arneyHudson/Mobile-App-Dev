package edu.msoe.dematsri.transcriptmanager

import android.content.Context
import androidx.room.Room
import edu.msoe.dematsri.transcriptmanager.database.ClassDetailDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.coroutines.coroutineContext

private const val DATABASE_NAME = "class-detail-database"

class ClassDetailRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {

    private val database:ClassDetailDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            ClassDetailDatabase::class.java,
            DATABASE_NAME
        ).build()

    fun getClassDetails(): Flow<List<ClassDetail>> = database.classDao().getClassDetails()
    suspend fun getClassDetail(id: UUID): ClassDetail = database.classDao().getClassDetail(id)
    suspend fun addClassDetail(classDetail: ClassDetail) = database.classDao().addClassDetail(classDetail)
    suspend fun deleteClassDetails() = database.classDao().deleteClassDetails()
    suspend fun deletethisClass(id: UUID) = database.classDao().deleteThisClass(id)
    suspend fun updateClassDetail(classDetail: ClassDetail) {
        database.classDao().updateClassDetail(classDetail)
    }

    fun updateClass(classDetail: ClassDetail) {
        coroutineScope.launch {
            updateClassDetail(classDetail)
        }
    }

    companion object {
        private var INSTANCE: ClassDetailRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ClassDetailRepository(context)
            }
        }

        fun get(): ClassDetailRepository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }

    }
}