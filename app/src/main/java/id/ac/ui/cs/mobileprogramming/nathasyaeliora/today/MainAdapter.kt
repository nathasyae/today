package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ContextUtils.getActivity
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Task
import kotlinx.android.synthetic.main.list_row_main.view.*
import java.security.AccessController.getContext

class MainAdapter(private val context: Context?, private val listener: (Task, Int) -> Unit) :
    RecyclerView.Adapter<TaskViewHolder>() {

    private var tasks = listOf<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_row_main,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        if (context != null) {
            holder.bindItem(context, tasks[position], listener)
        }
    }

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindItem(context: Context, task: Task, listener: (Task, Int) -> Unit) {
        itemView.task_title.text = task.title

        itemView.setOnClickListener {
            listener(task, layoutPosition)
        }
    }

}
