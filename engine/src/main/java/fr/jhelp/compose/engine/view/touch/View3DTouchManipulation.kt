package fr.jhelp.compose.engine.view.touch

import android.os.SystemClock
import fr.jhelp.compose.engine.view.CLICK_TIME
import fr.jhelp.compose.engine.view.View3D
import fr.jhelp.compose.math.distance
import fr.jhelp.compose.math.extensions.bounds

/**
 * Action to touch on [View3D] for classic manipulation of a node for show case
 */
object View3DTouchManipulation : View3DTouchAction()
{
    private var lastDownTime = 0L
    private lateinit var view3D: View3D

    override fun attachTo(view3D: View3D)
    {
        this.view3D = view3D
    }

    override fun detachFrom(view3D: View3D) = Unit

    override fun oneFingerDown(x: Float, y: Float)
    {
        this.lastDownTime = SystemClock.elapsedRealtime()
    }

    override fun oneFingerUp(x: Float, y: Float): Boolean
    {
        return SystemClock.elapsedRealtime() - this.lastDownTime <= CLICK_TIME
    }

    override fun oneFingerMove(previousX: Float, previousY: Float, x: Float, y: Float)
    {
        this.view3D.manipulateNode.position.angleY =
            (this.view3D.manipulateNode.position.angleY + (x - previousX) * 0.25f)
                .bounds(this.view3D.minimumAngleY, this.view3D.maximumAngleY)
        this.view3D.manipulateNode.position.angleX =
            (this.view3D.manipulateNode.position.angleX + (y - previousY) * 0.25f)
                .bounds(this.view3D.minimumAngleX, this.view3D.maximumAngleX)
    }

    override fun twoFingersDown(x1: Float, y1: Float,
                                x2: Float, y2: Float) = Unit

    override fun twoFingersUp(x1: Float, y1: Float,
                              x2: Float, y2: Float) = Unit

    override fun twoFingersMove(previousX1: Float, previousY1: Float, x1: Float, y1: Float,
                                previousX2: Float, previousY2: Float, x2: Float, y2: Float)
    {
        val previousDistance = distance(previousX1, previousY1, previousX2, previousY2)
        val currentDistance = distance(x1, y1, x2, y2)
        this.view3D.manipulateNode.position.z =
            (this.view3D.manipulateNode.position.z + 0.01f * (currentDistance - previousDistance))
                .bounds(this.view3D.minimumZ, this.view3D.maximumZ)

    }
}