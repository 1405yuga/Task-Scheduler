package com.example.taskscheduler

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "DisplayTasks tag"

class DisplayTasks : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_tasks)

        val gmail: String? = intent.getStringExtra("gmail")
        Log.d(TAG, "GMAIL : " + gmail)
    }
}