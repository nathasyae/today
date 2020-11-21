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
    lateinit var tasks: LiveData<List<Task>>


    fun insertTask(task: Task) {
        taskRepository.insert(task)
    }

    @JvmName("getTasks1")
    fun getTasks(): LiveData<List<Task>>? {
        return taskRepository.getTasks()
    }

    fun deleteTask(task: Task) {
        taskRepository.delete(task)
    }

    fun updateTask(task: Task) {
        taskRepository.update(task)
    }

}