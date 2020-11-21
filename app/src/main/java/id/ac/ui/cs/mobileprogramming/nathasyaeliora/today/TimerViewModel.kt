package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.database.AppDatabase
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Timer
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.repository.TimerRepository

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    private var timerRepository = TimerRepository(application)
    private var timers: LiveData<List<Timer>>? = timerRepository.getTimers()

    fun getTimerById(id: Int): LiveData<Timer>? {
        return timerRepository.getTimerById(id)
    }


}