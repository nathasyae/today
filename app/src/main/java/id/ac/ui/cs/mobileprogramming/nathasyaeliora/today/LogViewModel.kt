package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Log
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.repository.LogRepository

class LogViewModel(application: Application) : AndroidViewModel(application) {

    private var logRepository = LogRepository(application)
    private var logs: LiveData<List<Log>>? = logRepository.getLogs()

    fun insertLog(log: Log) {
        logRepository.insert(log)
    }

    fun getLogs(): LiveData<List<Log>>? {
        return logs
    }

    fun deleteLog(log: Log) {
        logRepository.delete(log)
    }

    fun updateLog(log: Log) {
        logRepository.update(log)
    }


}