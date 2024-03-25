package edu.msoe.dematsri.transcriptmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.msoe.dematsri.transcriptmanager.ClassDetail

@Database(entities = [ClassDetail::class], version=1)
@TypeConverters(ClassDetailTypeConverter::class)
abstract class ClassDetailDatabase : RoomDatabase() {
    abstract fun classDao(): ClassDao
}