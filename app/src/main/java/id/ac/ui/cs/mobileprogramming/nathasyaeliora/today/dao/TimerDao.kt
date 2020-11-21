package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Timer

@Dao
interface TimerDao {
    @Query("Select * from timer")
    fun getTimer(): LiveData<List<Timer>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTimer(timer: Timer)

    @Delete
    suspend fun deleteTimer(timer: Timer)

    @Update
    suspend fun updateTimer(timer: Timer)

    @Query("SELECT * FROM timer WHERE id=:id ")
    fun getTimerById(id: Int): LiveData<Timer>


}