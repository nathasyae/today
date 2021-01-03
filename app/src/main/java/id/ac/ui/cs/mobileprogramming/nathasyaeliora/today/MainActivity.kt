package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.*
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.provider.CalendarContract
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Task
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.service.BroadcastService
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.recycler_view
import java.util.*


class MainActivity : AppCompatActivity() {
    var TAG = "Main"

    private val RECORD_REQUEST_CODE = 101

    lateinit var display: TextView
    lateinit var startbutton: Button
    lateinit var pausebutton: Button
    lateinit var stopbutton: Button
    lateinit var handler: Handler
    lateinit var radioGroup: RadioGroup


    lateinit var txt: TextView
    lateinit var start_button: Button
    lateinit var reset_button: Button
    lateinit var add_button: Button
    lateinit var taskTitleInput: EditText
    lateinit var taskDetailInput: EditText
    lateinit var recyclerView: RecyclerView

    lateinit var session_str: String
    var session_duration: Long = 0

    private lateinit var mainViewModel: MainViewModel
    private lateinit var logViewModel: LogViewModel
    private lateinit var mainAdapter: MainAdapter

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val greetingresult = getGreeting(Calendar.getInstance()[Calendar.HOUR_OF_DAY])
        setContentView(R.layout.activity_main)
        greetings_text.text = greetingresult.toString()

        // MVVM SECTION
        recycler_view.layoutManager = LinearLayoutManager(this)
        mainAdapter = MainAdapter(this) { task, i ->
            showAlertMenu(task)
        }
        recycler_view.adapter = mainAdapter

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.getTasks()?.observe(this, Observer {
            mainAdapter.setTasks(it)
        })

        display = findViewById<TextView>(R.id.timer_countdown)
        startbutton = findViewById<Button>(R.id.start_button)
        pausebutton = findViewById<Button>(R.id.pause_button)
        stopbutton = findViewById<Button>(R.id.reset_button)
        handler = Handler()

        add_button = findViewById(R.id.add_button)
        add_button.setOnClickListener {
            permission_fn()
        }

        taskTitleInput = findViewById(R.id.tasktitle_input)
        taskDetailInput = findViewById(R.id.taskdetail_input)


        startbutton.setOnClickListener {
            startService(Intent(this, BroadcastService::class.java))
            Log.i(TAG, "Started service")
            startAlarm(30000)
        }


        stopbutton.setOnClickListener {
            stopService(Intent(this, BroadcastService::class.java))
            Log.i(TAG, "Stopped service")
            display.setText("00:00")
        }

        radioGroup = findViewById(R.id.session_radio)
    }

    // SERVICES

    val br: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                updateGUI(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(br, IntentFilter(BroadcastService.COUNTDOWN_BR))
        Log.i(TAG, "Registered broacast receiver")
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(br)
        Log.i(TAG, "Unregistered broacast receiver")
    }

    override fun onStop() {
        try {
            unregisterReceiver(br)
        } catch (e: Exception) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop()
    }

    override fun onDestroy() {
        stopService(Intent(this, BroadcastService::class.java))
        Log.i(TAG, "Stopped service")
        super.onDestroy()
    }

    private fun updateGUI(intent: Intent) {
        if (intent.extras != null) {
            val millisUntilFinished = intent.getLongExtra("countdown", 0)
            display.setText(
                String.format("%02d", (millisUntilFinished /1000 / 60)% 60)
                        + ":" + String.format("%02d", millisUntilFinished / 1000 % 60))
            Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000)
        }
    }

   // JNI

    external fun getGreeting(hour: Int): String

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }

    // ADD TASK

    fun addTask() {
        if (checkConnectivity(this)) {
            mainViewModel.insertTask(
                Task(taskTitleInput.text.toString(), taskDetailInput.text.toString())
            )
            addToCalendar(taskTitleInput.text.toString(), taskDetailInput.text.toString())

        } else {
            Toast.makeText(
                this,
                "Can't save. Please turn on the wifi/mobile data",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    fun onRadioButtonClick(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.pomodoro ->
                    if (checked) {
                        Log.i("RadioButton", "Pomo")
                        session_str = "Pomodoro"
                        session_duration = 25 * 60 * 60
                    }
                R.id.short_break ->
                    if (checked) {
                        Log.i("RadioButton", "S")
                        session_str = "Short Break"
                        session_duration = 5 * 60 * 60
                    }
                R.id.long_break ->
                    if (checked) {
                        Log.i("RadioButton", "L")
                        session_str = "Long Break"
                        session_duration = 15 * 60 * 60
                    }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
        fun startAlarm(duration: Long) {
            val millis = SystemClock.uptimeMillis() + duration
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
            Log.i("millis",millis.toString())
            alarmManager.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + duration,
                pendingIntent
            )
    }

    fun addToCalendar(tasktitle: String, taskdetail: String){
        var cr: ContentResolver? = this.getContentResolver()
        var cv: ContentValues = ContentValues()
        cv.put(CalendarContract.Events.TITLE, tasktitle)
        cv.put(CalendarContract.Events.DESCRIPTION, taskdetail)
        cv.put(CalendarContract.Events.DTSTART, Calendar.getInstance().getTimeInMillis());
        cv.put(
            CalendarContract.Events.DTEND,
            Calendar.getInstance().getTimeInMillis() + 60 * 60 * 1000
        );
        cv.put(CalendarContract.Events.CALENDAR_ID, 1)
        cv.put(CalendarContract.Events.EVENT_TIMEZONE, Calendar.getInstance().getTimeZone().getID())
        var uri: Uri = cr?.insert(CalendarContract.Events.CONTENT_URI, cv)!!

        Toast.makeText(this, "Successfully added to calendar", Toast.LENGTH_SHORT).show()
    }

    fun checkConnectivity(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    // MVVM

    private fun showAlertMenu(task: Task) {
        val items = arrayOf("Edit", "Delete")

        val builder =
            AlertDialog.Builder(this)
        builder.setItems(items) { dialog, which ->
            // the user clicked on colors[which]
            when (which) {
                0 -> {
                    showAlertDialogEdit(task)
                }
                1 -> {
                    mainViewModel.deleteTask(task)
                }
            }
        }
        builder.show()
    }

    private fun showAlertDialogEdit(task: Task) {
        val alert = AlertDialog.Builder(this)

        val editText = EditText(applicationContext)
        editText.setText(task.title)

        alert.setTitle("Edit Task")
        alert.setView(editText)

        alert.setPositiveButton("Update") { dialog, _ ->
            task.title = editText.text.toString()
            mainViewModel.updateTask(task)
            dialog.dismiss()
        }

        alert.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        alert.show()
    }


    private fun requestCalendarPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.WRITE_CALENDAR
            )
        ) {
            ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf(Manifest.permission.WRITE_CALENDAR),
                REQUEST_PERMISSION
            )

        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_CALENDAR),
                REQUEST_PERMISSION
            )
        }
    }

    private fun permission_fn() {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.WRITE_CALENDAR
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.WRITE_CALENDAR
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_CALENDAR),
                    REQUEST_PERMISSION
                )
            }
        } else {
            requestCalendarPermission()
        }
    }

    val REQUEST_PERMISSION = 1

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Thanks for enabling the permission", Toast.LENGTH_SHORT)
//                        .show()

                addTask()

            } else {
                android.app.AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("Calendar permission is needed to add task to your calendar. Please Allow.")
                        .setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, which ->
                                ActivityCompat.requestPermissions(
                                    this@MainActivity,
                                    arrayOf(Manifest.permission.WRITE_CALENDAR),
                                    REQUEST_PERMISSION
                                )
                            })
                        .setNegativeButton("Cancel",
                            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }).create()
                        .show()
                Toast.makeText(this, "Please allow the Permission", Toast.LENGTH_SHORT).show()
            }
        }
    }


}