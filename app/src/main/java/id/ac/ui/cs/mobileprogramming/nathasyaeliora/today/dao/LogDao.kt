package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Log

@Dao
interface LogDao {
    @Query("Select * from log")
    fun getLog(): LiveData<List<Log>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLog(log: Log)

    @Delete
    suspend fun deleteLog(log: Log)

    @Update
    suspend fun updateLog(log: Log)
}