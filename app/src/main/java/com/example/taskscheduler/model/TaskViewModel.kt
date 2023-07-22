package com.example.taskscheduler.model

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

private const val TAG = "TaskViewModel tag"
private val today = MaterialDatePicker.todayInUtcMilliseconds()

class TaskViewModel : ViewModel() {


    private var _userEmail = MutableLiveData<String>()
    private var _userDisplayName = MutableLiveData<String>()
    private var _userPhotoUrl = MutableLiveData<Uri>()

    val userEmail: LiveData<String> = _userEmail
    val userDisplayName: LiveData<String> = _userDisplayName
    val userPhotoUrl: LiveData<Uri> = _userPhotoUrl

    fun setUserData(email: String?, displayName: String?, photoUrl: Uri?) {
        this._userEmail.value = email
        this._userDisplayName.value = displayName
        this._userPhotoUrl.value = photoUrl
        Log.d(TAG, "Data ${userEmail.value} , ${userDisplayName.value} , ${userPhotoUrl.value}")
    }

    fun getFormattedDate(it: Long?): String {
        val selectedDate = Date(it!!)
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formattedDate = format.format(selectedDate)
        return formattedDate
    }

    private val TASK_DEFAULT = "UNKNOWN TASK"
    private var _taskName = MutableLiveData(TASK_DEFAULT)
    private var _taskDetails = MutableLiveData(TASK_DEFAULT)
    private var _taskDate = MutableLiveData(getFormattedDate(today))
    private var _taskTime = MutableLiveData(getFormattedTime(LocalTime.of(12,10)))

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

    fun getFormattedTime(now: LocalTime?): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())
        return now!!.format(formatter)
    }

    private val _tasksList = MutableLiveData<List<Task>>()
    val tasksList: LiveData<List<Task>> = _tasksList

    // TODO: get tasksList
}