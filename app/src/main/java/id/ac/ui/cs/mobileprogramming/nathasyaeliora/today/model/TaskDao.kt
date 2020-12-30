package id.ac.ui.cs.mobileprogramming.nathasyaeliora.today.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table ORDER BY title ASC")
    fun getAlphabetizedWords(): List<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Task)

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

}