package com.example.taskscheduler.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.taskscheduler.constants.ProjectConstants.USER_DEFAULT
import com.example.taskscheduler.model.Task
import com.google.firebase.firestore.FirebaseFirestore
private const val TAG = "Firestore tag"

object FirestoreFunctions {

    fun addTask(user: String, task: Task,context: Context) {
        if (user != USER_DEFAULT) {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection(user).add(task)
                .addOnSuccessListener {
                    Toast.makeText(context,"Task added",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Log.d(TAG,it.message.toString())
                    Toast.makeText(context,"Failed to add task",Toast.LENGTH_SHORT).show()
                }
        }
    }
}