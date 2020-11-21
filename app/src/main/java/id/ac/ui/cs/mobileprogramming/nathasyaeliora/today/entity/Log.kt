package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity

import java.util.*

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "log")
data class Log(
    @ColumnInfo(name = "session")
    var session: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    )