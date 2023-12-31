package com.example.taskscheduler.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.taskscheduler.constants.ProjectConstants.TIMESTAMP
import com.example.taskscheduler.constants.TimeConvertingFunctions
import com.example.taskscheduler.constants.TimeConvertingFunctions.convertTimestampToDateTime
import com.example.taskscheduler.constants.TimeConvertingFunctions.getDaysDifference
import com.example.taskscheduler.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

private const val TAG = "Firestore tag"

object FirestoreFunctions {

    fun addTask(task: Task, context: Context) {
        val firestore = FirebaseFirestore.getInstance()
        val userEmail = FirebaseAuth.getInstance().currentUser!!.email!!
        firestore.collection(userEmail).add(task)
            .addOnSuccessListener {
                Toast.makeText(context, "Task added", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.d(TAG, it.message.toString())
                Toast.makeText(context, "Failed to add task", Toast.LENGTH_SHORT).show()
            }

    }

    fun getTasks(
        context: Context,
        updateListLambda: (List<DocumentSnapshot>) -> (Unit)
    ) {
        val firestore = FirebaseFirestore.getInstance()
        val today =
            TimeConvertingFunctions.getFormattedDate(MaterialDatePicker.todayInUtcMilliseconds())
        val userEmail = FirebaseAuth.getInstance().currentUser!!.email!!
        firestore.collection(userEmail)
            .orderBy(TIMESTAMP, Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener {
                val newList = mutableListOf<DocumentSnapshot>()
                for (document in it.documents) {
                    val task = document.toObject(Task::class.java)
                    val (date, _) = convertTimestampToDateTime(task?.timestamp!!)
                    if (getDaysDifference(today, date) < -7) {
                        delTask(context, document, refreshLambda = {})
                    } else {
                        newList.add(document)
                    }
                }
                updateListLambda(newList)
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to load task", Toast.LENGTH_SHORT).show()
                Log.d(TAG, it.message.toString())
            }

    }

    fun delTask(
        context: Context,
        documentSnapshot: DocumentSnapshot,
        refreshLambda: () -> Unit
    ) {
        val firestore = FirebaseFirestore.getInstance()
        val userEmail = FirebaseAuth.getInstance().currentUser!!.email!!
        firestore.collection(userEmail).document(documentSnapshot.id).delete()
            .addOnSuccessListener {
                refreshLambda()
                Log.d(TAG, "Task deleted")
            }
            .addOnFailureListener {
                Toast.makeText(context, "Failed to delete task", Toast.LENGTH_SHORT).show()
                Log.d(TAG, it.message.toString())
            }

    }

    fun clearTasks(
        context: Context,
        refreshLambda: () -> Unit
    ) {
        val firestore = FirebaseFirestore.getInstance()
        val userEmail = FirebaseAuth.getInstance().currentUser!!.email!!
        firestore.collection(userEmail).get().addOnSuccessListener { querySnapshot ->
            for (query in querySnapshot) {
                query.reference.delete().addOnSuccessListener { refreshLambda() }
            }

        }.addOnFailureListener {
            Toast.makeText(context, "Failed to clear task", Toast.LENGTH_SHORT).show()
            Log.d(TAG, it.message.toString())
        }
    }

    fun deleteAccount(
        context: Context,
        skipToMainActivity: () -> Unit
    ) {
        val user = FirebaseAuth.getInstance().currentUser
        val email = user!!.email
        if (user.providerData.any { it.providerId == GoogleAuthProvider.PROVIDER_ID }) {
            user.delete()
                .addOnSuccessListener {
                    // Account deleted successfully
                    val firestore = FirebaseFirestore.getInstance()
                    if (email != null) {
                        firestore.collection(email).get().addOnSuccessListener { querySnapshot ->
                            for (query in querySnapshot) {
                                query.reference.delete()
                            }
                            skipToMainActivity()
                        }.addOnFailureListener {
                            Toast.makeText(context, "Failed to clear task", Toast.LENGTH_SHORT)
                                .show()
                            Log.d(TAG, it.message.toString())
                        }
                    }
                }
                .addOnFailureListener { deleteException ->
                    Toast.makeText(
                        context,
                        "Sign In again to confirm delete account",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, deleteException.message.toString())
                }
        }

    }
}

