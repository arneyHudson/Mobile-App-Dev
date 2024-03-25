package edu.msoe.demastri.lab5transcriptmanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.msoe.demastri.lab5transcriptmanager.database.dao.ClassDao
import edu.msoe.demastri.lab5transcriptmanager.database.dao.StudentDao
import edu.msoe.demastri.lab5transcriptmanager.database.dao.TermDao
import edu.msoe.demastri.lab5transcriptmanager.database.dao.TranscriptDao
import edu.msoe.demastri.lab5transcriptmanager.entities.ClassDetail
import edu.msoe.demastri.lab5transcriptmanager.entities.Student
import edu.msoe.demastri.lab5transcriptmanager.entities.Term
import edu.msoe.demastri.lab5transcriptmanager.entities.Transcript

@Database(entities = [Student::class, Term::class, ClassDetail::class, Transcript::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao
    abstract fun termDao(): TermDao
    abstract fun courseDetailDao(): ClassDao
    abstract fun transcriptDao(): TranscriptDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "my-app-database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
