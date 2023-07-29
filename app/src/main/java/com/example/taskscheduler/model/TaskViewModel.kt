package com.example.taskscheduler.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskscheduler.constants.ProjectConstants.TASK_DEFAULT
import com.example.taskscheduler.constants.TimeConvertingFunctions.getFormattedDate
import com.example.taskscheduler.constants.TimeConvertingFunctions.getFormattedTime
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.DocumentSnapshot
import java.time.LocalTime

private val today = MaterialDatePicker.todayInUtcMilliseconds()

class TaskViewModel : ViewModel() {

    private var _taskName = MutableLiveData(TASK_DEFAULT)
    private var _taskDetails = MutableLiveData(TASK_DEFAULT)
    private var _taskDate = MutableLiveData(getFormattedDate(today))
    private var _taskTime = MutableLiveData(getFormattedTime(LocalTime.of(12, 10)))

    val taskName: LiveData<String> = _taskName
    val taskDetails: LiveData<String> = _taskDetails
    val taskDate: LiveData<String> = _taskDate
    val taskTime: LiveData<String> = _taskTime

    fun setDate(date: String) {
        this._taskDate.value = date
    }

    fun setTime(time: String) {
        this._taskTime.value = time
    }

    fun setTask(name: String, details: String) {
        this._taskName.value = name
        this._taskDetails.value = details
    }

    var _tasksList = MutableLiveData(listOf<DocumentSnapshot>())
    val tasksList: LiveData<List<DocumentSnapshot>> = _tasksList

}

