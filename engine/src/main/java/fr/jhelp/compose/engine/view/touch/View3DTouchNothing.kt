package fr.jhelp.compose.engine.view.touch

import fr.jhelp.compose.engine.view.View3D

/**
 * Doe nothing when touch [View3D]
 */
object View3DTouchNothing : View3DTouchAction()
{
    override fun attachTo(view3D: View3D) = Unit

    override fun detachFrom(view3D: View3D) = Unit

    override fun oneFingerDown(x: Float, y: Float) = Unit

    override fun oneFingerUp(x: Float, y: Float): Boolean = false

    override fun oneFingerMove(previousX: Float, previousY: Float, x: Float, y: Float) = Unit

    override fun twoFingersDown(x1: Float, y1: Float,
                                x2: Float, y2: Float) = Unit

    override fun twoFingersUp(x1: Float, y1: Float,
                              x2: Float, y2: Float) = Unit

    override fun twoFingersMove(previousX1: Float, previousY1: Float, x1: Float, y1: Float,
                                previousX2: Float, previousY2: Float, x2: Float, y2: Float) = Unit
}