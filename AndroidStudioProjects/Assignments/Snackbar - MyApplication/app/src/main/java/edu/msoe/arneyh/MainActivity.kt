package edu.msoe.arneyh


import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import edu.msoe.arneyh.R.drawable
import edu.msoe.arneyh.R.id
import edu.msoe.arneyh.R.layout

class MainActivity : ComponentActivity() {
    private lateinit var msgButton: Button
    private lateinit var chgButton: Button
    private lateinit var imageView: ImageView
    private lateinit var mainView: ConstraintLayout
    private lateinit var fabButton: FloatingActionButton
    private var imageChanged: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        mainView = findViewById(id.mainView)
        msgButton = findViewById(id.msgButton)
        chgButton = findViewById(id.chgButton)
        imageView = findViewById(id.imageView)
        fabButton = findViewById(id.fabButton)

        // Add an initial image (You can replace with your own logic)
        imageView.setImageResource(drawable.picture)

        msgButton.setOnClickListener {
            // Show the message Snackbar when the button is clicked
            val snackbar = Snackbar.make(mainView, "Snackbar message made!!!", Snackbar.LENGTH_LONG)
            snackbar.show()
        }

        fabButton.setOnClickListener {
            // Make the SnackBar editable when the FAB is clicked
            val editText = EditText(this)
            editText.hint = "Edit the message"

            // Create the Snackbar with custom view (EditText) and duration
            val snackBar = Snackbar.make(mainView, "", Snackbar.LENGTH_INDEFINITE)
                .setAction("Save") {
                    // When the "Save" action is clicked, update the Snackbar message
                    val editedMessage = editText.text.toString()
                    Snackbar.make(mainView, "Edited message: $editedMessage", Snackbar.LENGTH_SHORT).show()
                }

            // Add the EditText view to the Snackbar
            //snackBar.view.addView(editText)
            // Show the Snackbar
            snackBar.show()
        }

        chgButton.setOnClickListener {
            imageChanged = !imageChanged
            if (imageChanged) {
                // Change the image when imageChanged is true
                imageView.setImageResource(drawable.picture2)
            } else {
                // Change the image when imageChanged is false
                //imageView.setImageResource(drawable.picture)
                val builder = AlertDialog.Builder(imageView.context)
                builder.setTitle("Debugging Assignment 1: Alert Dialog")
                builder.setMessage("Created by: Hudson Arney")
                builder.create()
                builder.show()
            }
        }
    }
}