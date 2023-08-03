package com.example.taskscheduler

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import com.example.taskscheduler.dataStore.PreferencesDataStore
import com.example.taskscheduler.adapters.TasksListAdapter
import com.example.taskscheduler.constants.TimeConvertingFunctions.convertDateTimeToTimestamp
import com.example.taskscheduler.constants.TimeConvertingFunctions.getFormattedDate
import com.example.taskscheduler.constants.TimeConvertingFunctions.getFormattedTime
import com.example.taskscheduler.databinding.ActivityDisplayTasksBinding
import com.example.taskscheduler.databinding.CardAddTaskBinding
import com.example.taskscheduler.databinding.CardSettingsLayoutBinding
import com.example.taskscheduler.firebase.FirestoreFunctions
import com.example.taskscheduler.firebase.FirestoreFunctions.addTask
import com.example.taskscheduler.firebase.FirestoreFunctions.getTasks
import com.example.taskscheduler.model.Task
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
import kotlinx.coroutines.launch
import java.time.LocalTime

class DisplayTasks : AppCompatActivity() {

    private lateinit var binding: ActivityDisplayTasksBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var viewModel: TaskViewModel
    private lateinit var tasksListAdapter: TasksListAdapter
    private lateinit var preferencesDataStore: PreferencesDataStore

    private var isLinearLayout = false

    override fun onStart() {
        super.onStart()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        refreshList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tasksListAdapter = TasksListAdapter(refreshlLambda = {
            refreshList()
        })
        setData()
        headerBinding()
        preferencesDataStore = PreferencesDataStore(applicationContext)
        preferencesDataStore.preferences.asLiveData().observe(this@DisplayTasks) {
            isLinearLayout = it
            switchLayout()
            switchIcon(binding.topAppBar.menu.getItem(0))
        }

        binding.recyclerView.adapter = tasksListAdapter

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_switch_layout -> {
                    //  change layout & change icon
                    isLinearLayout = !isLinearLayout
                    lifecycleScope.launch {
                        preferencesDataStore.saveLayoutPrefrence(applicationContext, isLinearLayout)
                    }
                    switchLayout()
                    switchIcon(menuItem)
                    true
                }

                else -> false
            }

        }
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.signOut -> {
                    openAlertDialog()
                    true
                }

                R.id.settings -> {
                    openSettingsDialog()
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
        viewModel.tasksList.observe(this@DisplayTasks) {
            tasksListAdapter.submitList(it)
        }

    }

    private fun switchIcon(menuItem: MenuItem?) {
        if (menuItem == null) return
        menuItem.icon =
            if (isLinearLayout) ContextCompat.getDrawable(this, R.drawable.staggered_layout_icon)
            else ContextCompat.getDrawable(this, R.drawable.linear_layout_icon)
    }

    private fun switchLayout() {
        if (isLinearLayout) binding.recyclerView.layoutManager = LinearLayoutManager(this)
        else binding.recyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
    }

    private fun refreshList() {
        getTasks(applicationContext, updateListLambda = {
            viewModel._tasksList.value = it
        })
    }

    private fun openAddDialog() {
        val dialog = Dialog(this)
        val cardBinding = CardAddTaskBinding.inflate(layoutInflater)
        dialog.setContentView(cardBinding.root)
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
        addDialogBinding(cardBinding, dialog)
    }

    private fun addDialogBinding(cardBinding: CardAddTaskBinding, dialog: Dialog) {
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
                if (task.editText?.text.toString().trim()
                        .isEmpty() || task.editText?.text.toString().length > 30
                ) {
                    task.error = "Task name should be of length (1 - 30)"
                } else if (taskDetails.editText?.text.toString().trim()
                        .isEmpty() || taskDetails.editText?.text.toString().length > 40
                ) {
                    taskDetails.error = "Task Details should be of length (1- 5000)"
                } else {
                    viewModel.setTask(
                        task.editText?.text.toString(),
                        taskDetails.editText?.text.toString()
                    )
                    val timestamp = convertDateTimeToTimestamp(
                        viewModel.taskDate.value.toString(),
                        viewModel.taskTime.value.toString()
                    )
                    val task =
                        Task(viewModel.taskName.value!!, viewModel.taskDetails.value!!, timestamp)
                    addTask(task, applicationContext)
                    refreshList()
                    dialog.dismiss()
                }
            }

        }
        viewModel.taskDate.observe(this@DisplayTasks) {
            cardBinding.dateTextView.text = it
        }
        viewModel.taskTime.observe(this@DisplayTasks) {
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
            val time = getFormattedTime(LocalTime.of(timePicker.hour, timePicker.minute))
            viewModel.setTime(time)
        }
        timePicker.addOnNegativeButtonClickListener {
            val time = getFormattedTime(LocalTime.now())
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
            val formattedDate = getFormattedDate(it)
            viewModel.setDate(formattedDate)
        }
        datePicker.addOnNegativeButtonClickListener {
            val formattedDate = getFormattedDate(today)
            viewModel.setDate(formattedDate)
        }
    }

    private fun openUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    private fun setData() {
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        //create viewmodel instance
        viewModel = ViewModelProvider(this, TaskViewModelFactory()).get(TaskViewModel::class.java)
    }

    private fun headerBinding() {
        val header = binding.navigationView.getHeaderView(0)
        header.apply {
            findViewById<ImageView>(R.id.user_image).load(firebaseUser.photoUrl) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.account_outline)
            }
            findViewById<TextView>(R.id.user_name).text = firebaseUser.displayName
            findViewById<TextView>(R.id.user_gmail).text = firebaseUser.email
        }
    }

    private fun openAlertDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.signout))
            .setMessage("Are you sure you want to sign Out?")
            .setPositiveButton("Yes") { dialog, _ ->
                // Respond to positive button press
                dialog.dismiss()
                skipToMainActivity()
            }
            .setNegativeButton("No") { _, _ ->
                Toast.makeText(this, "Sign Out cancelled", Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private val skipToMainActivity: () -> (Unit) = {
        mGoogleSignInClient.signOut().addOnCompleteListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun openSettingsDialog() {
        val dialog = Dialog(this)
        val cardBinding = CardSettingsLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(cardBinding.root)
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog.show()

        cardBinding.apply {
            clearAllTasks.setOnClickListener {
                FirestoreFunctions.clearTasks(applicationContext, refreshlLambda = {
                    refreshList()
                })
                dialog.dismiss()
                binding.drawerLayout.close()
            }

            deleteAccount.setOnClickListener {
                //  clear all tasks and del account
                dialog.dismiss()
                FirestoreFunctions.deleteAccount(applicationContext,skipToMainActivity)
            }
        }
    }
}