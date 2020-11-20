package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Task
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Task

@Dao
interface TaskDao {
    @Query("Select * from task")
    fun getTask(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)
}