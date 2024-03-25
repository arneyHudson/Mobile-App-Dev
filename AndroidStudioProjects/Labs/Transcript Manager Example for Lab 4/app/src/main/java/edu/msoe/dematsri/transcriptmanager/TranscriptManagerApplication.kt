package edu.msoe.dematsri.transcriptmanager

import android.app.Application

class TranscriptManagerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ClassDetailRepository.initialize(this)
    }
}