package com.example.taskscheduler.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TaskViewModel : ViewModel() {


    private var _userEmail = MutableLiveData<String>()
    private var _userDisplayName = MutableLiveData<String>()
    private var _userPhotoUri = MutableLiveData<String>()

    val userEmail : LiveData<String> = _userEmail
    val userDisplayName : LiveData<String> = _userDisplayName
    val userPhotoUri : LiveData<String> = _userPhotoUri

    private val _tasksList = MutableLiveData<List<Task>>()
    val tasksList : LiveData<List<Task>> = _tasksList

    // TODO: get tasksList
}