package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.repository

import android.app.Application
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.dao.TaskDao
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TaskRepository(application: Application) {

    private val taskDao: TaskDao?
    private var tasks: LiveData<List<Task>>? = null

    init {
        val db = AppDatabase.getInstance(application.applicationContext)
        taskDao = db?.taskDao()
        tasks = taskDao?.getTask()
    }

    fun getTasks(): LiveData<List<Task>>? {
        return tasks
    }

    fun insert(task: Task) = runBlocking {
        this.launch(Dispatchers.IO) {
            taskDao?.insertTask(task)
        }
    }

    fun delete(task: Task) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                taskDao?.deleteTask(task)
            }
        }
    }

    fun update(task: Task) = runBlocking {
        this.launch(Dispatchers.IO) {
            taskDao?.updateTask(task)
        }
    }

}