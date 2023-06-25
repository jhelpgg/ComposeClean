package fr.jhelp.android.library.engine.scene.geometry

import fr.jhelp.android.library.engine.scene.Object3D
import fr.jhelp.android.library.images.path.Path
import fr.jhelp.android.library.math.extensions.bounds
import fr.jhelp.android.library.math.extensions.nul
import kotlin.math.sqrt

/**
 * Object created with a path along an other path
 */
class ObjectPath(mainPath: Path, mainPathPrecision: Int = 5,
                 followPath: Path, followPathPrecision: Int = 5,
                 startU: Float = 0f, endU: Float = 1f,
                 startV: Float = 0f, endV: Float = 1f) : Object3D()
{
    init
    {
        this.createPath(mainPath, mainPathPrecision.bounds(2, 10),
                        followPath, followPathPrecision.bounds(2, 10),
                        startU, endU,
                        startV, endV)
    }

    private fun createPath(mainPath: Path, mainPathPrecision: Int,
                           followPath: Path, followPathPrecision: Int,
                           startU: Float, endU: Float,
                           startV: Float, endV: Float)
    {
        val mainList = mainPath.path(mainPathPrecision, startV, endV)
        val followList = followPath.path(followPathPrecision, startU, endU)

        if (mainList.isEmpty() || followList.isEmpty())
        {
            return
        }

        // Sides
        var x00: Float
        var y00: Float
        var z00: Float

        var u00: Float
        var v00: Float

        var x10: Float
        var y10: Float
        var z10: Float

        var u10: Float
        var v10: Float

        var x01: Float
        var y01: Float
        var z01: Float

        var u01: Float
        var v01: Float

        var x11: Float
        var y11: Float
        var z11: Float

        var u11: Float
        var v11: Float

        var x: Float
        var y: Float
        var a0: Float
        var b0: Float
        var a1: Float
        var b1: Float
        var xp0: Float
        var yp0: Float
        var xp1: Float
        var yp1: Float
        var xx: Float
        var yy: Float

        var length: Float

        for (segmentFollow in followList)
        {
            // U step goes (a0, b0) to (a1, b1), the direction vector is (x, y)
            a0 = segmentFollow.startX
            b0 = segmentFollow.startY
            a1 = segmentFollow.endX
            b1 = segmentFollow.endY
            x = a1 - a0
            y = b1 - b0

            // Normalize (x,y)
            length = sqrt((x * x) + (y * y))

            if (!length.nul)
            {
                x /= length
                y /= length
            }

            // Compute U values of each face at this step
            u00 = segmentFollow.startValue
            u01 = u00
            u10 = segmentFollow.endValue
            u11 = u10

            // For each step on V path
            for (segmentMain in mainList)
            {
                // V step goes (xp0, yp0) to (xp1, yp1), the direction vector is
                // (xx, yy)
                xp0 = segmentMain.startX
                yp0 = segmentMain.startY
                xp1 = segmentMain.endX
                yp1 = segmentMain.endY
                xx = xp1 - xp0
                yy = yp1 - yp0

                // Normalize(xx, yy)
                length = sqrt((xx * xx) + (yy * yy))

                if (!length.nul)
                {
                    xx /= length
                    yy /= length
                }

                // Compute V for the actual face
                v00 = segmentMain.startValue
                v10 = v00
                v01 = segmentMain.endValue
                v11 = v01

                // Up left position and normal
                x00 = a0 - (y * xp0)
                y00 = b0 + (x * xp0)
                z00 = yp0

                // Up right position and normal
                x10 = a1 - (y * xp0)
                y10 = b1 + (x * xp0)
                z10 = yp0

                // Down left position and normal
                x01 = a0 - (y * xp1)
                y01 = b0 + (x * xp1)
                z01 = yp1

                // Down right position and normal
                x11 = a1 - (y * xp1)
                y11 = b1 + (x * xp1)
                z11 = yp1

                // Create the face
                this.addSquare(x00, y00, z00, u00, v00,
                               x01, y01, z01, u01, v01,
                               x11, y11, z11, u11, v11,
                               x10, y10, z10, u10, v10)

            }
        }

        this.doubleFace = true
        this.seal()
    }

}
