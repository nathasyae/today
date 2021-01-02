package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.OpenGL

import android.content.Context
import android.opengl.GLSurfaceView
import id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.SplashActivity

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {
    private val renderer: MyGLRenderer

    init {

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2)

        renderer = MyGLRenderer()

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
    }
}