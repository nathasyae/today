package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity

import java.util.*

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "log")
data class Log(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

//    @ColumnInfo(name = "date")
//    var date: Date,

    @ColumnInfo(name = "session_qty")
    var session_qty: Int,

    @ColumnInfo(name = "task_completed_qty")
    var task_completed_qty: Int,

)