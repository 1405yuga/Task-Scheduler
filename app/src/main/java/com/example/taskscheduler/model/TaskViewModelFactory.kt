package com.example.taskscheduler.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TaskViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}