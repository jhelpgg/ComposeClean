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

    /**
     * Cut triangle in two along the longest edge
     */
    fun cutInTwo(): Pair<Triangle3D, Triangle3D>
    {
        val distanceFirstSecond =
            square(this.first.point3D.x - this.second.point3D.x) +
                    square(this.first.point3D.y - this.second.point3D.y) +
                    square(this.first.point3D.z - this.second.point3D.z)
        val distanceSecondThird =
            square(this.second.point3D.x - this.third.point3D.x) +
                    square(this.second.point3D.y - this.third.point3D.y) +
                    square(this.second.point3D.z - this.third.point3D.z)
        val distanceThirdFirst =
            square(this.third.point3D.x - this.first.point3D.x) +
                    square(this.third.point3D.y - this.first.point3D.y) +
                    square(this.third.point3D.z - this.first.point3D.z)

        return when
        {
            distanceFirstSecond >= distanceSecondThird && distanceFirstSecond >= distanceThirdFirst -> this.cutInFirstSecondEdge()

            distanceSecondThird >= distanceThirdFirst                                               -> this.cutInSecondThirdEdge()

            else                                                                                    -> this.cutInThirdFirstEdge()
        }
    }

    private fun cutInFirstSecondEdge(): Pair<Triangle3D, Triangle3D>
    {
        val vertex = Vertex(Point3D((this.first.point3D.x + this.second.point3D.x) / 2f,
                                    (this.first.point3D.y + this.second.point3D.y) / 2f,
                                    (this.first.point3D.z + this.second.point3D.z) / 2f),
                            Point2D((this.first.uv.x + this.second.uv.x) / 2f,
                                    (this.first.uv.y + this.second.uv.y) / 2f))
        return Pair(Triangle3D(this.first, vertex, this.third),
                    Triangle3D(vertex, this.second, this.third))
    }

    private fun cutInSecondThirdEdge(): Pair<Triangle3D, Triangle3D>
    {
        val vertex = Vertex(Point3D((this.second.point3D.x + this.third.point3D.x) / 2f,
                                    (this.second.point3D.y + this.third.point3D.y) / 2f,
                                    (this.second.point3D.z + this.third.point3D.z) / 2f),
                            Point2D((this.second.uv.x + this.third.uv.x) / 2f,
                                    (this.second.uv.y + this.third.uv.y) / 2f))
        return Pair(Triangle3D(this.first, this.second, vertex),
                    Triangle3D(this.first, vertex, this.third))
    }

    private fun cutInThirdFirstEdge(): Pair<Triangle3D, Triangle3D>
    {
        val vertex = Vertex(Point3D((this.third.point3D.x + this.first.point3D.x) / 2f,
                                    (this.third.point3D.y + this.first.point3D.y) / 2f,
                                    (this.third.point3D.z + this.first.point3D.z) / 2f),
                            Point2D((this.third.uv.x + this.first.uv.x) / 2f,
                                    (this.third.uv.y + this.first.uv.y) / 2f))
        return Pair(Triangle3D(this.first, this.second, vertex),
                    Triangle3D(vertex, this.second, this.third))
    }
}
