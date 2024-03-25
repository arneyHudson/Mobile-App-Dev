package edu.msoe.arneyh.lab5.entities;

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // Define the database schema, table, and column names
    companion object {
        const val DATABASE_NAME = "my_app_database"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "courses"
        const val COLUMN_ID = "_id"
        const val COLUMN_COURSE_NAME = "course_name"
        const val COLUMN_CREDITS = "credits"
        const val COLUMN_GRADE = "grade"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create the table
        val createTableSQL = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_COURSE_NAME TEXT,
                $COLUMN_CREDITS TEXT,
                $COLUMN_GRADE TEXT
            )
        """.trimIndent()
        db.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Implement database upgrade logic if needed
    }
}
