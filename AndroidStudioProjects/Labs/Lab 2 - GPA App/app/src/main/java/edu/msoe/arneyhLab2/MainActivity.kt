package edu.msoe.arneyhLab2

import android.R.attr.name
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayoutStates.TAG
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.toSpannable
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.analytics.HitBuilders.EventBuilder
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder
import com.google.firebase.analytics.FirebaseAnalytics
import edu.msoe.lab2.R
import edu.msoe.lab2.R.*
import edu.msoe.lab2.R.id.*


class MainActivity : ComponentActivity() {

    private lateinit var viewModel: InitialViewModel
    private lateinit var courseNameInput: AutoCompleteTextView
    private lateinit var creditsInput: AutoCompleteTextView
    private lateinit var gradeInput: AutoCompleteTextView
    private lateinit var addCourseButton: Button
    private lateinit var gpaResult: TextView
    private lateinit var resetButton: Button
    private lateinit var scrollView: ScrollView
    private lateinit var tableLayout: TableLayout
    private lateinit var roscoePicture: ImageView
    private lateinit var msoeLogo: ImageView
    private var courses = mutableListOf<Course>()
    private val maxClassLimit =
        999 // Not really needed anymore now that I have a scrollview but I had it before so I'll leave it for now
    private var currentClassCount = 0
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onDestroy() {
        super.onDestroy()
        setContentView(layout.activity_main)
        //restoreDisplayedData()
        // Load courses from the database and update the UI
        loadCoursesFromDatabase()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        initDatabase()
        viewModel = ViewModelProvider(
            this,
            SavedStateViewModelFactory(application, this)
        ).get(InitialViewModel::class.java)

        courseNameInput = findViewById(courseName)
        creditsInput = findViewById(credits)
        gradeInput = findViewById(grade)
        addCourseButton = findViewById(id.addCourseButton)
        gpaResult = findViewById(id.gpaResult)
        resetButton = findViewById(id.resetButton)
        scrollView = findViewById(id.scrollView)
        tableLayout = findViewById(id.tableLayout)
        roscoePicture = findViewById(id.roscoe)
        msoeLogo = findViewById(id.logo)
        roscoePicture.setImageResource(drawable.msoe)
        msoeLogo.setImageResource(drawable.msoe_logo)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState)
        }
        loadCoursesFromDatabase()
        setupCourseAdapter()

        val maxCourseNameLength = 100 // So people don't go typing a paragraph in the input
        val maxLengthFilter = InputFilter.LengthFilter(maxCourseNameLength)
        courseNameInput.filters = arrayOf(maxLengthFilter)

        courseNameInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Called when the text in courseNameInput changes
                val enteredCourseName = s.toString().trim()

                // Look up the credits for the entered course name
                val credits = CourseDatabase.getCourseCreditMap()[enteredCourseName]

                // Update the creditsInput field with the credits (if found)
                if (credits != null) {
                    creditsInput.text = Editable.Factory.getInstance().newEditable(credits)
                    gradeInput.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Empty
            }
        })

        val numericOnlyFilter = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (!Character.isDigit(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }
        creditsInput.filters = arrayOf(numericOnlyFilter)

        val positiveNumbersAndLettersFilter =
            InputFilter { source, start, end, dest, dstart, dend ->
                val input = source.subSequence(start, end).toString()

                // Regular expression to match positive numbers or letters (case-insensitive)
                val pattern = Regex("^[A-Za-z0-9]+$")

                if (pattern.matches(input)) {
                    null // Input is valid (positive numbers or letters)
                } else {
                    ""   // Input is invalid, reject it
                }
            }

        // Apply the custom InputFilter to the gradeInput EditText
        gradeInput.filters = arrayOf(positiveNumbersAndLettersFilter)

        courseNameInput.requestFocus()

        // Obtain the shared Tracker instance.
        val application = application as AnalyticsApplication
        val mTracker = application.defaultTracker
        Log.i(TAG, "Setting screen name: " + name)
        mTracker.setScreenName("Image~" + name)
        mTracker.send(ScreenViewBuilder().build())
        mTracker.send(
            EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build()
        )

        firebaseAnalytics.setCurrentScreen(this, "MainActivity", null)
        // ADD COURSE BUTTON
        addCourseButton.setOnClickListener {
            if (currentClassCount < maxClassLimit) {
                val courseName = courseNameInput.text
                val credits = creditsInput.text
                val grade = gradeInput.text

                if (areAllFieldsValid(courseName, credits, grade)) {
                    val dbHelper = DatabaseHelper(this)
                    val db = dbHelper.writableDatabase

                    val courseNameString = "\n" + courseName.toString().capitalize() + "\n"
                    val creditsString = "\n" + credits.toString() + "\n"
                    val gradeString = "\n" + checkNumeric(grade.toString()).toUpperCase() + "\n"
                    val course = Course(courseNameString, creditsString, gradeString)
                    courses.add(course)
                    courseNameInput.text.clear()
                    creditsInput.text.clear()
                    gradeInput.text.clear()

                    val values = ContentValues()
                    values.put(DatabaseHelper.COLUMN_COURSE_NAME, courseNameString)
                    values.put(DatabaseHelper.COLUMN_CREDITS, creditsString)
                    values.put(DatabaseHelper.COLUMN_GRADE, gradeString)
                    db.insert(DatabaseHelper.TABLE_NAME, null, values)
                    db.close()

                    createTableLayout(this, course)
                    shakeAnimation(tableLayout)
                    currentClassCount++
                    loadCoursesFromDatabase()
                    if (savedInstanceState != null) {
                        onSaveInstanceState(savedInstanceState)
                    }

                    // Hide the keyboard
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(addCourseButton.windowToken, 0)
                    scrollView.post {
                        scrollView.fullScroll(View.FOCUS_DOWN)
                    }
                    Toast.makeText(this, "Course added", Toast.LENGTH_SHORT).show()
                    courseNameInput.requestFocus()
                } else {
                    Toast.makeText(this, "Please enter valid course information", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(
                    this,
                    "You have reached the maximum limit of $maxClassLimit classes",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


        // RESET BUTTON
        resetButton.setOnClickListener {
            // Clear all course information and text views
            reset()
            if (savedInstanceState != null) {
                onSaveInstanceState(savedInstanceState)
            }
            Toast.makeText(this, "All course information has been cleared", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun areAllFieldsValid(
        courseName: Editable,
        credits: Editable,
        grade: Editable
    ): Boolean {
        return courseName.isNotBlank() && credits.isNotBlank() && grade.isNotBlank()
    }

    private fun createTableLayout(context: Context, course: Course) {
        val tableRow = TableRow(context)

        val courseNameTextView = createTextView(context, 500, course.name)
        val creditsTextView = createTextView(context, 350, course.credits)
        val gradeTextView = createTextView(context, 350, course.grade)

        tableRow.addView(courseNameTextView)
        tableRow.addView(creditsTextView)
        tableRow.addView(gradeTextView)

        tableLayout.addView(tableRow)
    }

    private fun createTextView(context: Context, width: Int, text: String): TextView {
        val textView = TextView(context)
        textView.width = width
        textView.typeface = ResourcesCompat.getFont(this, R.font.poppins)
        textView.gravity = Gravity.CENTER or Gravity.TOP
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white))
        textView.textSize = 18F
        textView.text = text
        return textView
    }

    private fun displayGpa() {
        if (viewModel.courses.isNotEmpty()) {
            calculateGpa()
            viewModel.gpaResult = gpaResult.text.toSpannable()
            shakeAnimation(gpaResult)
        }
    }

    private fun calculateGpa() {
        val totalCredits = viewModel.courses.sumByDouble { it.credits.toDouble() }
        val totalGradePoints =
            viewModel.courses.sumByDouble { it.credits.toDouble() * it.gradePoints }
        val gpa = if (totalCredits > 0) {
            totalGradePoints / totalCredits
        } else {
            0.0 // To prevent division by zero
        }
        val formattedGPA = String.format("%.3f", gpa)

        val honorsStatus = when {
            gpa >= 3.70 -> "GPA: $formattedGPA \n(High Honors)"
            gpa >= 3.20 -> "GPA: $formattedGPA \n(Dean's List)"
            gpa <= 2.0 -> "GPA: $formattedGPA \n(Academic Probation)"
            else -> "GPA: $formattedGPA"
        }

        val spannableString = SpannableString(honorsStatus)
        val colorGreen = ContextCompat.getColor(this, R.color.green)
        val colorBlue = ContextCompat.getColor(this, R.color.baby_blue)
        val colorRed = ContextCompat.getColor(this, R.color.red)
        val colorWhite = ContextCompat.getColor(this, R.color.white)
        spannableString.setSpan(
            ForegroundColorSpan(colorWhite),
            honorsStatus.indexOf("$formattedGPA"),
            honorsStatus.indexOf("$formattedGPA") + "$formattedGPA".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        if (honorsStatus.contains("(High Honors)")) {
            spannableString.setSpan(
                ForegroundColorSpan(colorGreen),
                honorsStatus.indexOf("(High Honors)"),
                honorsStatus.indexOf("(High Honors)") + "(High Honors)".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else if (honorsStatus.contains("(Dean's List)")) {
            spannableString.setSpan(
                ForegroundColorSpan(colorBlue),
                honorsStatus.indexOf("(Dean's List)"),
                honorsStatus.indexOf("(Dean's List)") + "(Dean's List)".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        } else if (honorsStatus.contains("(Academic Probation)")) {
            spannableString.setSpan(
                ForegroundColorSpan(colorRed),
                honorsStatus.indexOf("(Academic Probation)"),
                honorsStatus.indexOf("(Academic Probation)") + "(Academic Probation)".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        gpaResult.text = spannableString
    }

    // Animation widely used in the app when adding aspects
    private fun shakeAnimation(view: View) {
        view.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).withEndAction {
            view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(200)
        }
    }

    // Resets the views and inputs on the UI
    private fun reset() {
        viewModel.courses.clear()
        courses.clear()
        viewModel.courseName = ""
        viewModel.credits = ""
        viewModel.grade = ""
        courseNameInput.text.clear()
        creditsInput.text.clear()
        gradeInput.text.clear()
        gpaResult.text = "GPA: "
        tableLayout.removeAllViews()
        currentClassCount = 0

        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.writableDatabase
        db.delete(DatabaseHelper.TABLE_NAME, null, null)
        db.close()
    }

    private fun convertNumericToLetterGrade(numericGrade: Int): String {
        return when {
            numericGrade >= 93 -> "A"
            numericGrade >= 89 -> "AB"
            numericGrade >= 85 -> "B"
            numericGrade >= 81 -> "BC"
            numericGrade >= 77 -> "C"
            numericGrade >= 74 -> "CD"
            numericGrade >= 70 -> "D"
            numericGrade < 70 -> "F"
            else -> "F" // Default if not in any range
        }
    }

    // Change numeric grade to a letter grade for the UI
    private fun checkNumeric(grade: String): String {
        return if (grade.isNumeric()) {
            val numericGrade = grade.toInt()
            val newGrade = convertNumericToLetterGrade(numericGrade)
            newGrade // Return the new grade
        } else {
            grade // Return the original grade for non-numeric input
        }
    }

    @SuppressLint("Range")
    private fun restoreDisplayedData() {
        //gpaResult.text = viewModel.gpaResult
        courses = viewModel.courses

        loadCoursesFromDatabase()

        // Clear existing rows from the TableLayout
        tableLayout.removeAllViews()

        for (course in courses) {
            createTableLayout(this, course)
        }
    }

    @SuppressLint("Range")
    private fun loadCoursesFromDatabase() {
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.readableDatabase

        val cursor = db.query(
            DatabaseHelper.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        viewModel.courses.clear()

        while (cursor.moveToNext()) {
            val courseName =
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_COURSE_NAME))
            val credits = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CREDITS))
            val grade = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_GRADE))
            val course = Course(courseName, credits, grade)
            viewModel.courses.add(course)
        }

        cursor.close()
        db.close()

        // Update the UI with the loaded courses
        if (tableLayout.childCount == 0) {
            for (course in viewModel.courses) {
                createTableLayout(this, course)
            }
        }

        displayGpa()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        viewModel.saveState(outState)
        saveData()
    }

    override fun onRestoreInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
        viewModel.restoreState(bundle)
        restoreDisplayedData()
    }

    private fun saveData() {
        viewModel.courses = courses
    }

    private fun setupCourseAdapter() {
        val combinedCourseList = mutableListOf<String>()
        val resourceArrays = arrayOf(R.array.msoe_courses_array)

        val resources = resources
        for (resourceArray in resourceArrays) {
            combinedCourseList.addAll(resources.getStringArray(resourceArray).toList())
        }

        val adapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, combinedCourseList)
        courseNameInput.setAdapter(adapter)
    }

    private fun initDatabase() {
        val dbHelper = DatabaseHelper(this)
        val db = dbHelper.writableDatabase
        db.close()
    }
}


// Extension function to check if a string is numeric
fun String.isNumeric(): Boolean {
    return try {
        this.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}
