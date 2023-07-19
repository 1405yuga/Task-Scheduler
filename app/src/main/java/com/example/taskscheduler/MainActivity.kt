package com.example.taskscheduler

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.taskscheduler.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

private const val TAG = "MainActivity tag"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    var firebaseAuth = FirebaseAuth.getInstance()

//check if already signed in
    override fun onStart() {
        super.onStart()
        if(GoogleSignIn.getLastSignedInAccount(this)!=null){
            val email = firebaseAuth.currentUser?.email.toString()
            navigate(email)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configure Google Sign In options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        // getting the value of gso inside the GoogleSigninClient
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        // initialize the firebaseAuth variable
        firebaseAuth = FirebaseAuth.getInstance()

        binding.signInBtn.setOnClickListener {
            signInGoogle()
        }
    }

    //create intent to display available google signinOptions
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            Log.d(TAG, "res code ${it.resultCode} ${Activity.RESULT_OK} ${it.data}")
            if (it.resultCode == Activity.RESULT_OK) {
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(it.data)
                handleResult(task)
            }
        }

    //get the google account
    private fun handleResult(task: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)
            Log.d(TAG, "handle result $account")
            if (account != null) {
                //  navigate to display tasks
                getCredentials(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            Log.d(TAG, "handle result " + e.message)
        }
    }

    private fun getCredentials(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credentials).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navigate(account.email)

            }
        }
    }

    private fun navigate(email: String?) {
        val intent = Intent(this, DisplayTasks::class.java)
        intent.putExtra("gmail",email)
        startActivity(intent)
        finish()
    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        getResult.launch(signInIntent)

    }
}