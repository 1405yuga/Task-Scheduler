package com.example.taskscheduler.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ListItemTaskBinding = ListItemTaskBinding.inflate(layoutInflater,parent,false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tasksList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task : Task = tasksList.get(position)
        holder.bind(task)
    }


}