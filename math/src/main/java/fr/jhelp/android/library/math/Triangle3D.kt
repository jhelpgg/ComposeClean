package fr.jhelp.android.library.math

/**
 * Triangle in 3D space
 * @property first First vertex
 * @property second Second vertex
 * @property third Third vertex
 */
data class Triangle3D(val first: Vertex, val second: Vertex, val third: Vertex)
{
    /**
     * Triangle barycenter
     */
    val barycenter: Point3D =
        Point3D((this.first.point3D.x + this.second.point3D.x + this.third.point3D.x) / 3f,
                (this.first.point3D.y + this.second.point3D.y + this.third.point3D.y) / 3f,
                (this.first.point3D.z + this.second.point3D.z + this.third.point3D.z) / 3f)
}
