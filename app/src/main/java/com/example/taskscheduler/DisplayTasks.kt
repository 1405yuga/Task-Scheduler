package com.example.taskscheduler

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.taskscheduler.databinding.ActivityDisplayTasksBinding
import com.example.taskscheduler.model.TaskViewModel
import com.example.taskscheduler.model.TaskViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private const val TAG = "DisplayTasks tag"

class DisplayTasks : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayTasksBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var viewModel: TaskViewModel

    override fun onStart() {
        super.onStart()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        Log.d(
            TAG,
            "user details : ${viewModel.userEmail.value} ${viewModel.userDisplayName.value} ${viewModel.userPhotoUrl.value} "
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()
        headerBinding()

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.signOut -> {
                    createAlertDialog()
                    true
                }

                R.id.about -> {
                    openUrl("https://github.com/1405yuga/Task-Scheduler")
                    true
                }

                R.id.doc -> {
                    openUrl("https://github.com/1405yuga/Task-Scheduler/blob/main/README.md")
                    true
                }

                R.id.exit -> {
                    finish()
                    true
                }

                else -> false
            }

        }

    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun setData() {
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        //create viewmodel instance
        viewModel = ViewModelProvider(this, TaskViewModelFactory()).get(TaskViewModel::class.java)
        viewModel.setUserData(firebaseUser.email, firebaseUser.displayName, firebaseUser.photoUrl)
    }

    private fun headerBinding() {
        val header = binding.navigationView.getHeaderView(0)
        header.apply {
            viewModel.userPhotoUrl.observe(this@DisplayTasks) {
                findViewById<ImageView>(R.id.user_image).load(it) {
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.account_outline)
                }
            }
            viewModel.userDisplayName.observe(this@DisplayTasks) {
                findViewById<TextView>(R.id.user_name).text = it
            }
            viewModel.userEmail.observe(this@DisplayTasks) {
                findViewById<TextView>(R.id.user_gmail).text = it
            }
        }
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