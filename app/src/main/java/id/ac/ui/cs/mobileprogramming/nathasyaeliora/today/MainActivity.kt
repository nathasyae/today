package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.entity.Task
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.service.BroadcastService
import kotlinx.android.synthetic.main.fragment_first.*
import java.time.Instant.MAX


class MainActivity : AppCompatActivity() {
    var TAG = "Main"

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        add_button = findViewById(R.id.add_button)
        add_button.setOnClickListener {
            mainViewModel.insertTask(
                Task(taskTitleInput.text.toString(), taskDetailInput.text.toString())

            )
        }


        taskTitleInput = findViewById(R.id.tasktitle_input)
        taskDetailInput = findViewById(R.id.taskdetail_input)

        //
        // TIMER SECTION
        //
        txt = findViewById(R.id.timer_countdown)
        val intent = Intent(this, BroadcastService::class.java)

        start_button = findViewById(R.id.start_button)
        start_button.setOnClickListener {
            // broadcastService!!.startTimer()
            startService(intent)
            Log.i(TAG, "Started Service")
        }

        reset_button = findViewById(R.id.reset_button)
        reset_button.setOnClickListener {
            onStop()
        }

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

    //
    // SERVICES
    //
    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            //Update GUI
            updateGUI(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(broadcastReceiver, IntentFilter(BroadcastService.COUNTDOWN_BR))
        Log.i(TAG, "Registered broadcast receiver")
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
        Log.i(TAG, "Unregistered broadcast receiver")
    }

    override fun onStop() {
        try {
            unregisterReceiver(broadcastReceiver)
        } catch (e: Exception) {
            // Receiver was probably already
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
            val millisUntilFinished = intent.getLongExtra("countdown", 30000)
            Log.i(TAG, "Countdown seconds remaining:" + millisUntilFinished / 1000)
            txt!!.text = java.lang.Long.toString(millisUntilFinished / 1000)
            val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
            sharedPreferences.edit().putLong("time", millisUntilFinished).apply()
        }
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