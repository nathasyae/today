package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today

import android.R
import android.content.Intent
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.OpenGL.MyGLSurfaceView


class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    private lateinit var gLView: GLSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        gLView = MyGLSurfaceView(this)
        setContentView(gLView)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this, MainActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)

    }

    override fun onPause() {
        super.onPause()
//        oglView.onPause()
    }

    override fun onResume() {
        super.onResume()
//        oglView.onResume()
    }
}
