package edu.msoe.activity3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private lateinit var openWebPageButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openWebPageButton = findViewById(R.id.openWebPageButton)

        openWebPageButton.setOnClickListener {
            val url = "https://www.msoe.edu"
            val intent = Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));

            startActivity(intent);
        }
    }
}