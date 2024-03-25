package edu.msoe.dematsri.transcriptmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.Date
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    fun initStudent(studentRecord: StudentRecord) {
        studentRecord.courses.add(ClassDetail( UUID(10,10), "CSC 123", 4, "A", Date()))
        studentRecord.courses.add(ClassDetail( UUID(10,12), "CSC 124", 4, "B", Date()))
        studentRecord.courses.add(ClassDetail( UUID(10,14), "CSC 125", 4, "C", Date()))
    }

}