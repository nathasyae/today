package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "timer")
data class Timer(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "duration")
    var duration: Long
)