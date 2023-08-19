package fr.jhelp.android.library.engine.scene.morphing

import fr.jhelp.android.library.math.Triangle3D
import fr.jhelp.android.library.math.extensions.compare

/**
 * Permits to sort triangles for morphing preparation
 */
object MoprphingTriangleComparator : Comparator<Triangle3D>
{
    /**
     * Compore two given triangles to sort them
     */
    override fun compare(first: Triangle3D, second: Triangle3D): Int
    {
        val firstBarycenter = first.barycenter
        val secondBaryCenter = second.barycenter
        var comparison = firstBarycenter.z.compare(secondBaryCenter.z)

        if (comparison != 0)
        {
            return comparison
        }

        comparison = firstBarycenter.y.compare(secondBaryCenter.y)


        if (comparison != 0)
        {
            return comparison
        }

        return firstBarycenter.x.compare(secondBaryCenter.x)
    }
}
