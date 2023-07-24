package com.example.taskscheduler.adapters

import androidx.recyclerview.widget.ListAdapter
import com.example.taskscheduler.model.Task

class TasksListAdapter(private val updateList: (ArrayList<Task>) -> (Unit)) :
    ListAdapter<Task, TasksListAdapter.TaskViewHolder>(DiffCallBack) {

}