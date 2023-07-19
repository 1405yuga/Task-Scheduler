package com.example.taskscheduler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.taskscheduler.databinding.ActivityDisplayTasksBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

private const val TAG = "DisplayTasks tag"

class DisplayTasks : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayTasksBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.signOut -> {
                    // TODO: create alert dialog
                    mGoogleSignInClient.signOut().addOnCompleteListener {
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    true
                }
                else -> false
            }

        }

        val gmail: String? = intent.getStringExtra("gmail")
        Log.d(TAG, "GMAIL : " + gmail)


    }
}