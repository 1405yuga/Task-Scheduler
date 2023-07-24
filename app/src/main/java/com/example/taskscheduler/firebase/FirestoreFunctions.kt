package com.example.taskscheduler.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.taskscheduler.constants.ProjectConstants.TIMESTAMP
import com.example.taskscheduler.constants.ProjectConstants.USER_DEFAULT
import com.example.taskscheduler.model.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

private const val TAG = "Firestore tag"

object FirestoreFunctions {

    fun addTask(user: String, task: Task, context: Context) {
        if (user != USER_DEFAULT) {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection(user).add(task)
                .addOnSuccessListener {
                    Toast.makeText(context, "Task added", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Log.d(TAG, it.message.toString())
                    Toast.makeText(context, "Failed to add task", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun getTasks(user: String, context: Context,updateListLambda : (List<DocumentSnapshot>) -> (Unit)) {
        if (user != USER_DEFAULT) {
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection(user)
                .orderBy(TIMESTAMP, Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    updateListLambda(it.documents)
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to load task", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, it.message.toString())
                }
        }
    }

    fun deleteTask(user: String, context: Context,documentSnapshot: DocumentSnapshot){
        if(user!= USER_DEFAULT){
            val firestore = FirebaseFirestore.getInstance()
            firestore.collection(user).document(documentSnapshot.id).delete()
                .addOnSuccessListener {
                    Toast.makeText(context,"Task deleted",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context,"Failed to delete task",Toast.LENGTH_SHORT).show()
                    Log.d(TAG, it.message.toString())
                }
        }
    }
}