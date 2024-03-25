package edu.msoe.firebase

import android.R
import android.R.attr.name
import android.app.Application
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.analytics.HitBuilders.EventBuilder
import com.google.android.gms.analytics.HitBuilders.ScreenViewBuilder
import edu.msoe.firebase.ui.theme.FirebaseTheme
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.Tracker


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

        // Obtain the shared Tracker instance.
        // Obtain the shared Tracker instance.
        val application = application as AnalyticsApplication
        var mTracker = application.defaultTracker

        Log.i(TAG, "Setting screen name: " + name)
        mTracker!!.setScreenName("Image~" + name)
        mTracker!!.send(ScreenViewBuilder().build())

        mTracker.send(
            EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build()
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FirebaseTheme {
        Greeting("Android")
    }
}

/*
 * Copyright Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * This is a subclass of [Application] used to provide shared objects for this app, such as
 * the [Tracker].
 */
class AnalyticsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        sAnalytics = GoogleAnalytics.getInstance(this)
    }

    @get:Synchronized
    val defaultTracker: Tracker?
        /**
         * Gets the default [Tracker] for this [Application].
         * @return tracker
         */
        get() {
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            if (sTracker == null) {
                sTracker = sAnalytics?.newTracker(R.xml.global_tracker)
            }
            return sTracker
        }

    companion object {
        private var sAnalytics: GoogleAnalytics? = null
        private var sTracker: Tracker? = null
    }
}
