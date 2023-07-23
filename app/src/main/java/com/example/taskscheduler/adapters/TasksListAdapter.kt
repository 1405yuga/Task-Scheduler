package com.example.taskscheduler.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.taskscheduler.constants.TimeConvertingFunctions.convertTimestampToDateTime
import com.example.taskscheduler.databinding.ListItemTaskBinding
import com.example.taskscheduler.model.Task

class TasksListAdapter(private val context : Context,private val tasksList : List<Task>) : RecyclerView.Adapter<TasksListAdapter.TaskViewHolder>() {

    // holds the view
    class TaskViewHolder(val binding : ListItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task : Task){
            binding.apply {
                taskName.text = task.taskName
                details.text = task.taskDetails
                dateTime.text = convertTimestampToDateTime(task.timestamp!!)
            }
        }
    }

}