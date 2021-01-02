package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today

import android.Manifest
import android.animation.ValueAnimator
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.provider.CalendarContract
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Task
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.service.BroadcastService
import kotlinx.android.synthetic.main.fragment_first.*
import java.util.*

class AnimationActivity : AppCompatActivity() {
    protected lateinit var rocket: View
    protected lateinit var frameLayout: View
    protected var screenHeight = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.animation)

        rocket = findViewById(R.id.rocket)
        frameLayout = findViewById(R.id.container)
        frameLayout.setOnClickListener { onStartAnimation() }
    }

    override fun onResume() {
        super.onResume()

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        screenHeight = displayMetrics.heightPixels.toFloat()
    }

    companion object {
        val DEFAULT_ANIMATION_DURATION = 2500L
    }

    fun onStartAnimation() {
        val valueAnimator = ValueAnimator.ofFloat(0f, -screenHeight)

        valueAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            rocket.translationY = value
        }

        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.duration = DEFAULT_ANIMATION_DURATION

        valueAnimator.start()
    }
}