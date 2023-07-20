package com.example.taskscheduler.model

import com.google.firebase.Timestamp

data class Task(val taskName : String, val taskDetails:String, val timestamp: Timestamp)