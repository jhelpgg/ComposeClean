package fr.jhelp.android.library.engine.view

import android.opengl.GLSurfaceView
import fr.jhelp.android.library.engine.scene.Scene3D
import fr.jhelp.android.library.math.Point2D
import fr.jhelp.android.library.math.Point3D
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Renderer used to initialize OpenGL and update it
 */
internal class View3DRenderer(private val refreshDone: () -> Unit) : GLSurfaceView.Renderer
{
    private val viewBoundsMutableState: MutableStateFlow<ViewBounds> =
        MutableStateFlow<ViewBounds>(ViewBounds())
    val viewBoundsState: StateFlow<ViewBounds> = this.viewBoundsMutableState.asStateFlow()
    val scene3D = Scene3D(this.viewBoundsState)

    override fun onDrawFrame(gl: GL10)
    {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        gl.glMatrixMode(GL10.GL_MODELVIEW)
        this.scene3D.render(gl)
        this.refreshDone()
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int)
    {
        gl.glViewport(0, 0, width, height)

        val ratio = width.toFloat() / height
        gl.glMatrixMode(GL10.GL_PROJECTION)
        gl.glMatrixMode(GL10.GL_MODELVIEW)
        gl.glLoadIdentity()
        gl.glFrustumf(-ratio, ratio, -1f, 1f, 1f, 10f)

        this.viewBoundsMutableState.value = ViewBounds(Point3D(-ratio, 1f, 1f),
                                                       Point3D(ratio, -1f, 10f),
                                                       Point2D(0f, 0f),
                                                       Point2D(width.toFloat(), height.toFloat()))
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig?)
    {
        gl.glDisable(GL10.GL_DITHER)
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST)
        gl.glClearColor(1f, 1f, 1f, 1f)
        gl.glEnable(GL10.GL_DEPTH_TEST)
        gl.glEnable(GL10.GL_ALPHA_TEST)
        // Set alpha precision
        gl.glAlphaFunc(GL10.GL_GREATER, 0.01f)
        // Way to compute alpha
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)

        // We accept blending
        gl.glEnable(GL10.GL_BLEND)
        gl.glEnable(GL10.GL_COLOR_MATERIAL)

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
    }
}