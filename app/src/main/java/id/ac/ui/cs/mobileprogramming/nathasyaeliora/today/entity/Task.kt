package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "detail")
    var detail: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)