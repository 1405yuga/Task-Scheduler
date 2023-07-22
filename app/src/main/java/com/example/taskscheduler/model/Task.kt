package com.example.taskscheduler.model

import com.google.firebase.Timestamp

data class Task(val taskName : String? =null, val taskDetails:String?=null, val timestamp: Timestamp?=null)