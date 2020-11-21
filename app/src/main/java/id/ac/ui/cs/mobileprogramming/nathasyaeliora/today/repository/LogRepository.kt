package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.dao.LogDao
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Log
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Timer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LogRepository(application: Application) {

    private val logDao: LogDao?
    private var logs: LiveData<List<Log>>? = null

    init {
        val db = AppDatabase.getInstance(application.applicationContext)
        logDao = db?.logDao()
        logs = logDao?.getLog()
    }


    fun getLogs(): LiveData<List<Log>>? {
        return logs
    }

    fun insert(log: Log) = runBlocking {
        this.launch(Dispatchers.IO) {
            logDao?.insertLog(log)
        }
    }

    fun delete(log: Log) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                logDao?.deleteLog(log)
            }
        }
    }

    fun update(log: Log) = runBlocking {
        this.launch(Dispatchers.IO) {
            logDao?.updateLog(log)
        }
    }

}