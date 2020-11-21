package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer")
data class Timer(
    @ColumnInfo(name = "type")
    var type: String,
    @ColumnInfo(name = "duration")
    var duration: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)