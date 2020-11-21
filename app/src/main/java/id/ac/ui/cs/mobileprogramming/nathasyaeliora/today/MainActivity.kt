package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today

import android.Manifest
import android.content.*
import android.content.pm.PackageManager.PERMISSION_GRANTED
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
import kotlinx.android.synthetic.main.fragment_first.recycler_view
import java.util.*


class MainActivity : AppCompatActivity() {
    var TAG = "Main"
    val callbackId = 42

    var boolstart: Boolean = false;
    var startTime: Long = 0;
    var seconds:Long=0;
    var pauseTime:Long=0;
    var minutes:Long=0;
    var hours:Long=0;
    var pause:Long=0;

    lateinit var display: TextView
    lateinit var startbutton: Button
    lateinit var pausebutton: Button
    lateinit var stopbutton: Button
    lateinit var handler: Handler


    lateinit var txt: TextView
    lateinit var start_button: Button
    lateinit var reset_button: Button
    lateinit var add_button: Button
    lateinit var taskTitleInput: EditText
    lateinit var taskDetailInput: EditText
    lateinit var recyclerView: RecyclerView

    var broadcastService: BroadcastService? = null

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mainAdapter: MainAdapter

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission(
            callbackId,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
        );

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
            mainViewModel.insertTask(
                Task(taskTitleInput.text.toString(), taskDetailInput.text.toString())
            )
            addToCalendar(taskTitleInput.text.toString(), taskDetailInput.text.toString())
        }


        taskTitleInput = findViewById(R.id.tasktitle_input)
        taskDetailInput = findViewById(R.id.taskdetail_input)

        val runnable = object : Runnable {

            override fun run() {
                if (boolstart == false) {
                    boolstart = true
                    display.text = "Pause"
                } else {
                    boolstart = false
                    display.text = "Resume"
                }
                seconds = SystemClock.uptimeMillis() / 1000 + pauseTime - startTime
                pause = seconds
                minutes = seconds / 60
                seconds = seconds % 60
                hours = hours / 60
                minutes = minutes % 60


                display.setText(
                    String.format("%02d", hours) + ":"
                            + String.format("%02d", minutes) + ":"
                            + String.format("%02d", seconds)
                )

                handler.postDelayed(this, 0)
            }

        }
        startbutton.setOnClickListener {
            startbutton.visibility= View.GONE
            pausebutton.visibility=View.VISIBLE
            startTime = SystemClock.uptimeMillis() / 1000;
            handler.postDelayed(runnable, 0);
        }

        pausebutton.setOnClickListener {
            startbutton.visibility= View.VISIBLE
            pausebutton.visibility=View.INVISIBLE
            handler.removeCallbacks(runnable)
            pauseTime=pause
        }

        stopbutton.setOnClickListener {
            startbutton.visibility=View.VISIBLE
            pausebutton.visibility=View.INVISIBLE
            pauseTime=0;
            handler.removeCallbacks(runnable)
            display.setText("00:00:00")
        }

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

    //
    // MVVM
    //

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


    private fun checkPermission(callbackId: Int, vararg permissionsId: String) {
        var permissions = true
        for (p in permissionsId) {
            permissions =
                permissions && ContextCompat.checkSelfPermission(this, p) == PERMISSION_GRANTED
        }
        if (!permissions) ActivityCompat.requestPermissions(this, permissionsId, callbackId)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> true
//            else -> super.onOptionsItemSelected(item)
//        }
//    }


}