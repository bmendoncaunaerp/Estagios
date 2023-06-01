package br.unaerp.estagios

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.unaerp.estagios.databinding.TaskItemBinding

class TaskListAdapter: Adapter<TaskListAdapter.TaskViewHolder>() {
    private val taskList: MutableList<String> = mutableListOf()
    fun updateList(newTaskList: List<String>) {
        taskList.clear()
        taskList.addAll(newTaskList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(taskList[position])
    }

    inner class TaskViewHolder(private val binding: TaskItemBinding) : ViewHolder(binding.root) {
        fun bind(task: String) {
            binding.cbTask.text = task
        }
    }
}