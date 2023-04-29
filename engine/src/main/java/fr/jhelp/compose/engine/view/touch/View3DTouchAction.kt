package fr.jhelp.compose.engine.view.touch

import fr.jhelp.compose.engine.view.View3D

/**
 * Action to touch on [View3D]
 */
abstract class View3DTouchAction
{
    /**
     * Called when reaction to touch is attach to the view 3D
     */
    abstract fun attachTo(view3D: View3D)

    /**
     * Called when reaction to touch is detach from the view 3D
     */
    abstract fun detachFrom(view3D: View3D)

    /**
     * Called when one finger touch the 3D view
     */
    abstract fun oneFingerDown(x: Float, y: Float)

    /**
     * Returned boolean indicates if have to simulate click if available
     */
    abstract fun oneFingerUp(x: Float, y: Float): Boolean

    /**
     * Called when on finger slide on 3D view
     */
    abstract fun oneFingerMove(previousX: Float, previousY: Float, x: Float, y: Float)

    /**
     * Called when two finger are down in 3D view
     */
    abstract fun twoFingersDown(x1: Float, y1: Float,
                                x2: Float, y2: Float)

    /**
     * Called when two finger removed from 3D view
     */
    abstract fun twoFingersUp(x1: Float, y1: Float,
                              x2: Float, y2: Float)

    /**
     * Called when two fingers slides on 3D view
     */
    abstract fun twoFingersMove(previousX1: Float, previousY1: Float, x1: Float, y1: Float,
                                previousX2: Float, previousY2: Float, x2: Float, y2: Float)
}
