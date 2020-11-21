package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.dao.LogDao
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.dao.TaskDao
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.dao.TimerDao
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Log
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Task
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Timer

@Database(entities = [Task::class, Log::class, Timer::class],exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun logDao(): LogDao
    abstract fun timerDao(): TimerDao


    companion object {

        private const val DB_NAME = "TODAY_DB"
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class) {
                    instance = Room
                        .databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            DB_NAME
                        )
                        .build()
                }
            }
            return instance
        }

//        private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){};
    }
}