package fr.jhelp.android.library.math

/**
 * Vertex represents a point in space with associated UV
 * @property point3D Point in space
 * @property uv Associated UV
 */
data class Vertex(val point3D: Point3D, val uv: Point2D)
{
    /**
     * Create a scaled version
     */
    fun scale(scale: Float): Vertex =
        Vertex(this.point3D.scale(scale), this.uv)

}
