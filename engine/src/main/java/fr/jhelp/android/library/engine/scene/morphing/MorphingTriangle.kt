package fr.jhelp.android.library.engine.scene.morphing

import fr.jhelp.android.library.math.Point2D
import fr.jhelp.android.library.math.Point3D
import fr.jhelp.android.library.math.Triangle3D
import fr.jhelp.android.library.math.Vertex
import fr.jhelp.android.library.math.extensions.bounds
import fr.jhelp.android.library.math.extensions.nul
import fr.jhelp.android.library.math.extensions.same

/**
 * Triangle for morphing.
 * It interpolates a triangle to an other one
 *
 * @property source Triangle morphing source
 * @property destination Triangle morphing destination
 */
class MorphingTriangle(val source: Triangle3D, val destination: Triangle3D)
{
    /**
     * Computes interpolation between triangles
     * @param percent percentage of interpolation progression in [[0, 1]]
     */
    fun interpolate(percent: Float): Triangle3D
    {
        val factor = percent.bounds(0f, 1f)

        return when
        {
            factor.nul      -> this.source
            factor.same(1f) -> this.destination
            else            -> this.interpolation(factor)
        }
    }

    private fun interpolation(percent: Float): Triangle3D
    {
        val firstSource = this.source.first
        val secondSource = this.source.second
        val thirdSource = this.source.third
        val firstDestination = this.destination.first
        val secondDestination = this.destination.second
        val thirdDestination = this.destination.third
        val antiPercent = 1f - percent
        return Triangle3D(
            Vertex(
                Point3D(firstSource.point3D.x * antiPercent + firstDestination.point3D.x * percent,
                        firstSource.point3D.y * antiPercent + firstDestination.point3D.y * percent,
                        firstSource.point3D.z * antiPercent + firstDestination.point3D.z * percent),
                Point2D(firstSource.uv.x * antiPercent + firstDestination.uv.x * percent,
                        firstSource.uv.y * antiPercent + firstDestination.uv.y * percent)),
            Vertex(
                Point3D(secondSource.point3D.x * antiPercent + secondDestination.point3D.x * percent,
                        secondSource.point3D.y * antiPercent + secondDestination.point3D.y * percent,
                        secondSource.point3D.z * antiPercent + secondDestination.point3D.z * percent),
                Point2D(secondSource.uv.x * antiPercent + secondDestination.uv.x * percent,
                        secondSource.uv.y * antiPercent + secondDestination.uv.y * percent)),
            Vertex(
                Point3D(thirdSource.point3D.x * antiPercent + thirdDestination.point3D.x * percent,
                        thirdSource.point3D.y * antiPercent + thirdDestination.point3D.y * percent,
                        thirdSource.point3D.z * antiPercent + thirdDestination.point3D.z * percent),
                Point2D(thirdSource.uv.x * antiPercent + thirdDestination.uv.x * percent,
                        thirdSource.uv.y * antiPercent + thirdDestination.uv.y * percent)))
    }
}
