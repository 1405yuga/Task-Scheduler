package com.example.taskscheduler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskscheduler.constants.TimeConvertingFunctions.convertTimestampToDateTime
import com.example.taskscheduler.databinding.ListItemTaskBinding
import com.example.taskscheduler.firebase.FirestoreFunctions.delTask
import com.example.taskscheduler.model.Task
import com.google.firebase.firestore.DocumentSnapshot

private const val TAG = "TasksListAdapter tag"

class TasksListAdapter :
    ListAdapter<DocumentSnapshot, TasksListAdapter.TaskViewHolder>(DiffCallBack) {

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<DocumentSnapshot>() {
            override fun areItemsTheSame(
                oldItem: DocumentSnapshot,
                newItem: DocumentSnapshot
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DocumentSnapshot,
                newItem: DocumentSnapshot
            ): Boolean {
                return oldItem.toObject(Task::class.java) == newItem.toObject(Task::class.java)
            }
        }
    }

    //holds view
    class TaskViewHolder(private val binding: ListItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            documentSnapshot: DocumentSnapshot
        ) {
            val task: Task? = documentSnapshot.toObject(Task::class.java)
            binding.apply {
                taskName.text = task?.taskName
                details.text = task?.taskDetails
                val (dateValue, timeValue) = convertTimestampToDateTime(task?.timestamp!!)
                date.text = dateValue
                time.text = timeValue
                deleteTask.setOnClickListener {
                    //delTask(binding.root.context, documentSnapshot, refreshList)

                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ListItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}