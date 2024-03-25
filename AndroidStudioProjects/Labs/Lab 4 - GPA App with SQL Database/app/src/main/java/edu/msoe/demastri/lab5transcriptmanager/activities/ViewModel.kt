package edu.msoe.demastri.lab5transcriptmanager.activities

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import androidx.core.text.toSpannable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import edu.msoe.demastri.lab5transcriptmanager.entities.Course

class InitialViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val defaultCourses: MutableList<Course> = mutableListOf()
    private val defaultGpaResult: Spannable = SpannableString("GPA: ")
    private val defaultCourseName: String = ""
    private val defaultCredits: String = ""
    private val defaultGrade: String = ""

    private val COURSES_KEY = "courses"
    private val GPA_TEXTVIEW_KEY = "gpaResultText"
    private val COURSE_NAME_KEY = "courseName"
    private val CREDITS_KEY = "credits"
    private val GRADES_KEY = "grades"


    // Property to access and update the course list
    var courses: MutableList<Course>
        get() = savedStateHandle.get(COURSES_KEY) ?: defaultCourses
        set(value) {
            savedStateHandle.set(COURSES_KEY, value)
        }

    var gpaResult: Spannable
        get() = savedStateHandle[GPA_TEXTVIEW_KEY] ?: defaultGpaResult
        set(value) {
            savedStateHandle[GPA_TEXTVIEW_KEY] = value
        }

    var courseName: String
        get() = savedStateHandle[COURSE_NAME_KEY] ?: defaultCourseName
        set(value) {
            savedStateHandle[COURSE_NAME_KEY] = value
        }

    var credits: String
        get() = savedStateHandle[CREDITS_KEY] ?: defaultCredits
        set(value) {
            savedStateHandle[CREDITS_KEY] = value
        }

    var grade: String
        get() = savedStateHandle[GRADES_KEY] ?: defaultGrade
        set(value) {
            savedStateHandle[GRADES_KEY] = value
        }

    fun saveState(bundle: Bundle) {
        bundle.putString(COURSE_NAME_KEY, courseName)
        bundle.putString(CREDITS_KEY, credits)
        bundle.putString(GRADES_KEY, grade)
        bundle.putCharSequence(GPA_TEXTVIEW_KEY, gpaResult).toString().toSpannable()
    }

    fun restoreState(bundle: Bundle) {
        courseName = bundle.getString(COURSE_NAME_KEY, "")
        credits = bundle.getString(CREDITS_KEY, "")
        grade = bundle.getString(GRADES_KEY, "")
        gpaResult = bundle.getCharSequence(GPA_TEXTVIEW_KEY, SpannableString("GPA: ")).toSpannable()
    }
}