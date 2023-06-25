package fr.jhelp.android.library.engine.view.touch

import fr.jhelp.android.library.engine.view.View3D

/**
 * Doe nothing when touch [View3D]
 */
object View3DTouchNothing : View3DTouchAction()
{
    override fun attachTo(view3D: View3D): Unit = Unit

    override fun detachFrom(view3D: View3D): Unit = Unit

    override fun oneFingerDown(x: Float, y: Float): Unit = Unit

    override fun oneFingerUp(x: Float, y: Float): Boolean = false

    override fun oneFingerMove(previousX: Float, previousY: Float, x: Float, y: Float): Unit = Unit

    override fun twoFingersDown(x1: Float, y1: Float,
                                x2: Float, y2: Float): Unit = Unit

    override fun twoFingersUp(x1: Float, y1: Float,
                              x2: Float, y2: Float): Unit = Unit

    override fun twoFingersMove(previousX1: Float, previousY1: Float, x1: Float, y1: Float,
                                previousX2: Float, previousY2: Float, x2: Float, y2: Float): Unit =
        Unit
}