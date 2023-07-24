package com.example.taskscheduler.adapters

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskscheduler.constants.TimeConvertingFunctions.convertTimestampToDateTime
import com.example.taskscheduler.databinding.ListItemTaskBinding
import com.example.taskscheduler.model.Task

class TasksListAdapter(private val updateList: (ArrayList<Task>) -> (Unit)) :
    ListAdapter<Task, TasksListAdapter.TaskViewHolder>(DiffCallBack) {

    //holds view
    class TaskViewHolder(private val binding : ListItemTaskBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(task : Task){
            binding.apply {
                binding.apply {
                    taskName.text = task.taskName
                    details.text = task.taskDetails
                    val(dateValue,timeValue)= convertTimestampToDateTime(task.timestamp!!)
                    date.text = dateValue
                    time.text = timeValue
                }
            }
        }

    }

}