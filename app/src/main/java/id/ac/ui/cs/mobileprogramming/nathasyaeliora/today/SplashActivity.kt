package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today

import android.content.Intent
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.OpenGL.MyGLSurfaceView

class SplashActivity : AppCompatActivity() {
    private lateinit var gLView: GLSurfaceView

    // This is the loading time of the splash screen
    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gLView = MyGLSurfaceView(this)
        setContentView(gLView)

        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start your app main activity

            startActivity(Intent(this,MainActivity::class.java))

            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }

}