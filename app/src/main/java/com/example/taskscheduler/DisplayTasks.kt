package com.example.taskscheduler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskscheduler.databinding.ActivityDisplayTasksBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private const val TAG = "DisplayTasks tag"

class DisplayTasks : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayTasksBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onStart() {
        super.onStart()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            Log.d(TAG, "navigation clicked")
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.signOut -> {
                    createAlertDialog()
                    true
                }
                else -> false
            }

        }

        val gmail: String? = intent.getStringExtra("gmail")
        Log.d(TAG, "GMAIL : " + gmail)


    }

    private fun createAlertDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.signout))
            .setMessage("Are you sure you want to sign Out?")
            .setPositiveButton("Yes") { dialog, which ->
                // Respond to positive button press
                mGoogleSignInClient.signOut().addOnCompleteListener {
                    val intent = Intent(this, MainActivity::class.java)
                    dialog.dismiss()
                    startActivity(intent)
                    finish()
                }
            }
            .setNegativeButton("No") { dialog, which ->
                Toast.makeText(this, "Sign Out cancelled", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .show()
    }
}