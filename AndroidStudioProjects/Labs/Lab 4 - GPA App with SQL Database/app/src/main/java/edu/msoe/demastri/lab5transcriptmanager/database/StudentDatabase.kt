package edu.msoe.demastri.lab5transcriptmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.msoe.demastri.lab5transcriptmanager.database.dao.ClassDao
import edu.msoe.demastri.lab5transcriptmanager.entities.ClassDetail

@Database(entities = [StudentDatabase::class], version=1)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao(): ClassDao
}