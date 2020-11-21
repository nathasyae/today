package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.repository

import android.app.Application
import androidx.lifecycle.LiveData
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.dao.TimerDao
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Timer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TimerRepository(application: Application) {

    private val timerDao: TimerDao?
    private var timers: LiveData<List<Timer>>? = null

    init {
        val db = AppDatabase.getInstance(application.applicationContext)
        timerDao = db?.timerDao()
        timers = timerDao?.getTimer()

//        timerDao.insertTimer(Timer("Pomodoro", 25*60*60))

    }


    fun getTimers(): LiveData<List<Timer>>? {
        return timers
    }

    fun insert(timer: Timer) = runBlocking {
        this.launch(Dispatchers.IO) {
            timerDao?.insertTimer(timer)
        }
    }

    fun delete(timer: Timer) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                timerDao?.deleteTimer(timer)
            }
        }
    }

    fun update(timer: Timer) = runBlocking {
        this.launch(Dispatchers.IO) {
            timerDao?.updateTimer(timer)
        }
    }

}