package fr.jhelp.compose.engine.view.touch

import fr.jhelp.compose.engine.view.View3D

/**
 * Action to touch on [View3D]
 */
abstract class View3DTouchAction
{
    abstract fun attachTo(view3D: View3D)

    abstract fun detachFrom(view3D: View3D)

    abstract fun oneFingerDown(x: Float, y: Float)

    /**
     * Returned boolean indicates if have to simulate click if available
     */
    abstract fun oneFingerUp(x: Float, y: Float): Boolean

    abstract fun oneFingerMove(previousX: Float, previousY: Float, x: Float, y: Float)

    abstract fun twoFingersDown(x1: Float, y1: Float,
                                x2: Float, y2: Float)

    abstract fun twoFingersUp(x1: Float, y1: Float,
                              x2: Float, y2: Float)

    abstract fun twoFingersMove(previousX1: Float, previousY1: Float, x1: Float, y1: Float,
                                previousX2: Float, previousY2: Float, x2: Float, y2: Float)
}
