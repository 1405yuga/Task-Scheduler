package com.example.taskscheduler.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {

    private val _tasksList = MutableLiveData<List<Task>>()
    val tasksList : LiveData<List<Task>> = _tasksList
}