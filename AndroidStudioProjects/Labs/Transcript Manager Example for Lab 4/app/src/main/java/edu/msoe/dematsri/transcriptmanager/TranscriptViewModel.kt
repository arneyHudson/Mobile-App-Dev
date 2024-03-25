package edu.msoe.dematsri.transcriptmanager

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID
import kotlin.coroutines.CoroutineContext

private const val TAG = "TranscriptViewModel"

class TranscriptViewModel : ViewModel() {
    private val classDetailRepository = ClassDetailRepository.get()
    var classes = classDetailRepository.getClassDetails()

    suspend fun addClassDetail(classDetail: ClassDetail) {
        classDetailRepository.addClassDetail(classDetail)
    }

    val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    //init {
        //Log.d( TAG, "building classes")
        //CoroutineScope(coroutineContext).launch {
        //    classes += loadClasses()
        // }
        //Log.d( TAG, "building classes complete")
    //}

    //suspend fun loadClasses(): List<ClassDetail> {
    //    CoroutineScope(coroutineContext).launch {

            //classDetailRepository.deleteClassDetails()  // remove when comfy with db work
            //classes = loadClassesFromLocal()
    //        classes = loadClassesFromDB()
    //    }
    //    return classes
    //}

    suspend fun loadClassesFromLocal(): List<ClassDetail> {
        Log.d(TAG, "local building classes within suspend fn")
        val result = mutableListOf<ClassDetail>()
        //delay(5000)
        for (i in 0 until 100) {
            var thisCredit = 3
            var thisGrade = "A"
            if (i % 2 == 0)
                thisCredit = 4
            if (i % 3 == 1)
                thisGrade = "B"
            if (i % 3 == 2)
                thisGrade = "C"
            val classDetail = ClassDetail(
                id = UUID.randomUUID(),
                courseID = "CSC #$i",
                credits = thisCredit,
                grade = thisGrade,
                startDate = Date()
            )
            result += classDetail
        }
        Log.d(TAG, "local building classes within suspend fn complete")
        return result
    }

    suspend fun loadClassesFromDB(): List<ClassDetail> {
        Log.d(TAG, "building classes from db")
        var result: List<ClassDetail> = mutableListOf<ClassDetail>()
        //result = classDetailRepository.getClassDetails()

        if (result == null || result.size == 0) {
            Log.d(TAG, " - no classes found, building and adding to db")
            result = loadClassesFromLocal()
            Log.d(TAG, " - " + result.size + " classes built, adding to db")
            //for (c: ClassDetail in result) {
            //   classDetailRepository.addClassDetail(c)
            //}
        }
        Log.d(TAG, " - leaving with " + result.size + " classes")

        Log.d(TAG, "building classes from db complete")
        return result
    }
}