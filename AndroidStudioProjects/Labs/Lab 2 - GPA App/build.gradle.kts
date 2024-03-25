// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    //id("com.google.gms.google-services")
}

buildscript {
    repositories {
        // Check that you have the following line (if not, add it):

        google()  // Google's Maven repository
    }
    dependencies {
        // Add this line
        classpath("com.google.gms:google-services:4.4.0")
    }
}

allprojects {
    repositories {
        // Check that you have the following line (if not, add it):
        //google()  // Google's Maven repository
    }
}
