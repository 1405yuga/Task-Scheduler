package com.example.taskscheduler.model

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "TaskViewModel tag"

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

    private val TASK_DEFAULT ="UNKNOWN TASK"
    private var _taskName = MutableLiveData(TASK_DEFAULT)
    private var _taskDetails = MutableLiveData(TASK_DEFAULT)
    private var _taskDate = MutableLiveData<String>()
    private var _taskTime = MutableLiveData<String>()

    val taskName: LiveData<String> = _taskName
    val taskDetails: LiveData<String> = _taskDetails
    val taskDate: LiveData<String> = _taskDate
    val taskTime: LiveData<String> = _taskTime

    private val _tasksList = MutableLiveData<List<Task>>()
    val tasksList: LiveData<List<Task>> = _tasksList

    // TODO: get tasksList
}