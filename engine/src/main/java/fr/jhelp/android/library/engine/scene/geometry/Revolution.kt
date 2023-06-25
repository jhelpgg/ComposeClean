/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.android.library.engine.scene.geometry

import fr.jhelp.android.library.engine.scene.Object3D
import fr.jhelp.android.library.images.path.Path
import fr.jhelp.android.library.math.extensions.bounds
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * A revolution is a path draw in (X, Y) plane and rotate around Y axis.
 *
 * By example for a bottle:
 *
 *     Y
 *     |
 *     |..
 *     | .
 *     |  .
 *     |  .
 *     |  .
 *     |  .
 *     |...
 *     |
 *
 * Where the dot are the path, and vertical bar is Y axis.
 *
 * If rotate around Y axis, the result is the bottle
 * @documentation geometry/Revolution.md
 * @param path Path to use
 * @param angle Angle to do the revolution around Y axis in degree
 * @param multiplierU Multiplier of U to be repeat texture around the object
 * @param startV V at path beginning
 * @param endV V at path ending
 * @param pathPrecision Precision used by the path. Less precision faster but less smooth border
 * @param rotationPrecision Precision used by rotation. Less precision, less round but faster
 */
class Revolution(path: Path, angle: Float = 360f,
                 multiplierU: Float = 1f, startV: Float = 0f, endV: Float = 1f,
                 pathPrecision: Int = 5, rotationPrecision: Int = 12) : Object3D()
{
    init
    {
        this.computeMesh(path, Math.toRadians(angle.toDouble().bounds(0.0, 360.0)),
                         multiplierU, startV, endV,
                         pathPrecision.bounds(2, 10), rotationPrecision.bounds(3, 24))
    }

    private fun computeMesh(path: Path, radian: Double,
                            multiplierU: Float, startV: Float, endV: Float,
                            pathPrecision: Int, rotationPrecision: Int)
    {
        var angle: Double
        var angleFuture: Double
        var cos: Double
        var cosFuture: Double
        var sin: Double
        var sinFuture: Double
        var x0: Double
        var y0: Double
        var x1: Double
        var y1: Double
        var vx: Double
        var vy: Double
        var u0: Double
        var u1: Double
        var v0: Double
        var v1: Double
        var length: Double

        var xAA: Float
        var yAA: Float
        var zAA: Float
        var uAA: Float
        var vAA: Float
        var xAF: Float
        var yAF: Float
        var zAF: Float
        var uAF: Float
        var vAF: Float
        var xFA: Float
        var yFA: Float
        var zFA: Float
        var uFA: Float
        var vFA: Float
        var xFF: Float
        var yFF: Float
        var zFF: Float
        var uFF: Float
        var vFF: Float

        var an: Int

        val list = path.path(pathPrecision, startV, endV)

        // For each line of the path
        for (segment in list)
        {
            // Get start and end point
            x0 = segment.startX.toDouble()
            y0 = segment.startY.toDouble()
            v0 = segment.startValue.toDouble()

            x1 = segment.endX.toDouble()
            y1 = segment.endY.toDouble()
            v1 = segment.endValue.toDouble()

            // Compute the vector start to end and normalize it
            vx = x1 - x0
            vy = y1 - y0

            length = sqrt(vx * vx + vy * vy)
            if (abs(length) >= 1e-5)
            {
                vx /= length
                vy /= length
            }

            // For each rotation step
            an = 0
            while (an < rotationPrecision)
            {
                // Compute U
                u0 = (an * multiplierU / rotationPrecision).toDouble()
                u1 = ((an + 1f) * multiplierU / rotationPrecision).toDouble()

                // Compute angles, cosinus and sinus
                angle = radian * an / rotationPrecision
                angleFuture = radian * (an + 1) / rotationPrecision

                cos = cos(angle)
                sin = sin(angle)
                cosFuture = cos(angleFuture)
                sinFuture = sin(angleFuture)

                // Compute each vertex
                xAA = (cos * x0).toFloat()
                yAA = y0.toFloat()
                zAA = (-sin * x0).toFloat()
                uAA = u0.toFloat()
                vAA = v0.toFloat()

                xAF = (cos * x1).toFloat()
                yAF = y1.toFloat()
                zAF = (-sin * x1).toFloat()
                uAF = u0.toFloat()
                vAF = v1.toFloat()

                xFA = (cosFuture * x0).toFloat()
                yFA = y0.toFloat()
                zFA = (-sinFuture * x0).toFloat()
                uFA = u1.toFloat()
                vFA = v0.toFloat()

                xFF = (cosFuture * x1).toFloat()
                yFF = y1.toFloat()
                zFF = (-sinFuture * x1).toFloat()
                uFF = u1.toFloat()
                vFF = v1.toFloat()

                // Draw the face
                this.addSquare(xAF, yAF, zAF, uAF, vAF,
                               xFF, yFF, zFF, uFF, vFF,
                               xFA, yFA, zFA, uFA, vFA,
                               xAA, yAA, zAA, uAA, vAA)

                an++
            }
        }

        this.seal()
        val center = this.center()
        this.position.position(-center.x, -center.y, -center.z)
    }
}