package com.example.taskscheduler.model

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.taskscheduler.adapters.TasksListAdapter
import com.example.taskscheduler.constants.ProjectConstants.TASK_DEFAULT
import com.example.taskscheduler.constants.ProjectConstants.USER_DEFAULT
import com.example.taskscheduler.constants.TimeConvertingFunctions.getFormattedDate
import com.example.taskscheduler.constants.TimeConvertingFunctions.getFormattedTime
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.DocumentSnapshot
import java.time.LocalTime

private const val TAG = "TaskViewModel tag"
private val today = MaterialDatePicker.todayInUtcMilliseconds()

class TaskViewModel : ViewModel() {


    private var _userEmail = MutableLiveData(USER_DEFAULT)
    private var _userDisplayName = MutableLiveData(USER_DEFAULT)
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

    val tasksListAdapter = TasksListAdapter()
    val updateList: (List<DocumentSnapshot>) -> (Unit) = {
        if(userEmail.value.toString() != USER_DEFAULT){
            tasksListAdapter.submitList(it)
        }
    }
}