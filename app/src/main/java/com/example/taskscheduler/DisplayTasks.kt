package com.example.taskscheduler

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.taskscheduler.databinding.ActivityDisplayTasksBinding
import com.example.taskscheduler.databinding.CardAddTaskBinding
import com.example.taskscheduler.model.TaskViewModel
import com.example.taskscheduler.model.TaskViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.time.LocalTime

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
                    openAlertDialog()
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

        binding.addBtn.setOnClickListener { openAddDialog() }


    }

    private fun openAddDialog() {
        val dialog = Dialog(this)
        val cardBinding = CardAddTaskBinding.inflate(layoutInflater)
        dialog.setContentView(cardBinding.root)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.horizontalMargin = 0.1f / 6

        dialog.window?.attributes = layoutParams
        dialog.show()

        addDialogBinding(cardBinding)
    }

    private fun addDialogBinding(cardBinding: CardAddTaskBinding) {
        cardBinding.apply {

            date.setOnClickListener {
                //  add date picker
                createDatePicker()
            }
            time.setOnClickListener {
                //  add time picker
                createTimePicker()
            }
            addButton.setOnClickListener {
                // TODO: set task
            }

        }
        viewModel.taskDate.observe(this@DisplayTasks) {
            cardBinding.dateTextView.text = it
        }
        viewModel.taskTime.observe(this@DisplayTasks){
            cardBinding.timeTextView.text = it
        }
    }

    private fun createTimePicker() {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(10)
            .setTitleText("Select Task time")
            .build()

        timePicker.show(supportFragmentManager, "TIME PICK")

        // create time picker BUTTONS
        timePicker.addOnPositiveButtonClickListener {
            val time = viewModel.getFormattedTime(LocalTime.of(timePicker.hour, timePicker.minute))
            viewModel.setTime(time)
        }
    }

    private fun createDatePicker() {
        val today = MaterialDatePicker.todayInUtcMilliseconds()

        // date picker constraints
        val constraintsBuilder = CalendarConstraints.Builder()
            .setStart(today)
            .setOpenAt(today)
            .setValidator(DateValidatorPointForward.from(today))

        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Task date")
                .setSelection(today)
                .setCalendarConstraints(constraintsBuilder.build())
                .build()
        datePicker.show(supportFragmentManager, "DATE PICK")

        // create date picker BUTTONS
        datePicker.addOnPositiveButtonClickListener {
            val formattedDate = viewModel.getFormattedDate(it)
            viewModel.setDate(formattedDate)
        }
        datePicker.addOnDismissListener {
            //  assign today's date to variable
            viewModel.setDate(viewModel.getFormattedDate(today))
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

    private fun openAlertDialog() {
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