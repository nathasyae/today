package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Task
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.repository.TaskRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var taskRepository = TaskRepository(application)
    private var tasks: LiveData<List<Task>>? = taskRepository.getTasks()

    fun insertTask(task: Task) {
        taskRepository.insert(task)
    }

    fun getTasks(): LiveData<List<Task>>? {
        return tasks
    }

    fun deleteTask(task: Task) {
        taskRepository.delete(task)
    }

    fun updateTask(task: Task) {
        taskRepository.update(task)
    }
}