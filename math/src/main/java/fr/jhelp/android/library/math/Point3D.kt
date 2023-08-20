package fr.jhelp.android.library.math

/**
 * A point 3D with real coordinates
 * @property x Point abscissa
 * @property y Point ordinate
 * @property z Point depth
 */
data class Point3D(val x: Float = 0f, val y: Float = 0f, val z: Float = 0f)
{
    /**
     * Create a scaled version
     */
    fun scale(scale: Float): Point3D =
        Point3D(this.x * scale, this.y * scale, this.z * scale)
}
