package fr.jhelp.android.library.engine.scene.morphing

import fr.jhelp.android.library.math.Point3D
import fr.jhelp.android.library.math.Triangle3D
import fr.jhelp.android.library.math.square

internal class NearInfo(triangle3D: Triangle3D, list:List<Triangle3D>)
{
    val nearestIndex : Int
    val nearestTriangle : Triangle3D

    init
    {
        var nearestIndex = 0
        var nearestTriangle  = list[0]
        var distanceMin = this.distance(triangle3D.barycenter, nearestTriangle.barycenter)

        for((index, triangle) in list.withIndex()) {
            val distance =  this.distance(triangle3D.barycenter, triangle.barycenter)

            if(distance<distanceMin)
            {
                nearestIndex = index
                nearestTriangle = triangle
                distanceMin = distance
            }
        }

        this.nearestIndex = nearestIndex
        this.nearestTriangle = nearestTriangle
    }

    private fun distance(point1: Point3D, point2: Point3D): Float =
        square(point1.x - point2.x) + square(point1.y - point2.y) + square(point1.z - point2.z)

}