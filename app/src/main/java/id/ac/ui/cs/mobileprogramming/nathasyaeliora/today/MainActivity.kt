package id.ac.ui.cs.mobileprogramming.nathasyaeliora.today

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import android.Manifest.permission.FOREGROUND_SERVICE
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    val textView:TextView by lazy { findViewById<EditText>(R.id.timer_countdown) }
    val editText:TextView by lazy {findViewById<TextView>(R.id.input_time)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, arrayOf<String>(FOREGROUND_SERVICE), PackageManager.PERMISSION_GRANTED)
        val intentFilter = IntentFilter()
        intentFilter.addAction("Counter")
        val broadcastReceiver = object:BroadcastReceiver() {
            override fun onReceive(context:Context, intent:Intent) {
                val integerTime = intent.getIntExtra("TimeRemaining", 0)
                textView.setText(integerTime.toString())
            }
        }
        registerReceiver(broadcastReceiver, intentFilter)

        // NAVIGATION
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun startButton(view: View) {
        val intentService = Intent(this, TimerService::class.java)
        val integerTimeSet = Integer.parseInt(editText.getText().toString())
        intentService.putExtra("TimeValue", integerTimeSet)
        startService(intentService)
    }
}