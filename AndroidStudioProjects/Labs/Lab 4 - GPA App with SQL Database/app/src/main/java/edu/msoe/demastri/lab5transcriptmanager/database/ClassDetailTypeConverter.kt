package edu.msoe.demastri.lab5transcriptmanager.database

import androidx.room.TypeConverter
import java.util.Date

class ClassDetailTypeConverter {
    @TypeConverter
    fun fromDate( date:Date): Long {
        return date.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date {
        return Date(millisSinceEpoch)
    }
}