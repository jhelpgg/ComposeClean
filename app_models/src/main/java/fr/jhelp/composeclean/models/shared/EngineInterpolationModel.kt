package fr.jhelp.composeclean.models.shared

import androidx.annotation.DrawableRes
import fr.jhelp.compose.engine.scene.Scene3D

/**
 * Interpolations in animation show case
 */
interface EngineInterpolationModel
{
    /**
     * Define the scene and launch animation
     */
    fun scene(scene: Scene3D, @DrawableRes texture: Int)

    /**
     * Stop animation
     */
    fun stopAnimation()
}
