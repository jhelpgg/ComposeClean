/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.android.library.engine.scene.geometry

import fr.jhelp.android.library.engine.scene.Object3D
import fr.jhelp.android.library.math.extensions.bounds
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * A sphere
 */
class Sphere(multiplierU: Float = 1f, multiplierV: Float = 1f, slice: Int = 16, slack: Int = 16) :
    Object3D()
{
    init
    {
        this.computeSphereMesh(slice.bounds(2, 32), slack.bounds(2, 32),
                               multiplierU, multiplierV)
    }

    /**
     * Compute mesh of the sphere
     *
     * @param slice Number of slice
     * @param stack Number of stack
     * @param multiplierU Number of repetition of U
     * @param multiplierV Number of repetition of V
     */
    private fun computeSphereMesh(slice: Int, stack: Int, multiplierU: Float, multiplierV: Float)
    {
        // Angles compute for slice and stack
        var sliceAngle: Double
        var stackAngle: Double
        var sliceAngleFuture: Double
        var stackAngleFuture: Double

        // Cosinus and sinus of angles
        var cosSliceAngle: Double
        var cosStackAngle: Double
        var cosSliceAngleFuture: Double
        var cosStackAngleFuture: Double
        var sinSliceAngle: Double
        var sinStackAngle: Double
        var sinSliceAngleFuture: Double
        var sinStackAngleFuture: Double

        // Computed UV
        var uA: Float
        var vA: Float
        var uF: Float
        var vF: Float

        // Computed normals
        var nxAA: Float
        var nyAA: Float
        var nzAA: Float
        var nxFA: Float
        var nyFA: Float
        var nzFA: Float
        var nxAF: Float
        var nyAF: Float
        var nzAF: Float
        var nxFF: Float
        var nyFF: Float
        var nzFF: Float

        // To walk throw slice and stack
        var sli = 0
        var sta: Int

        while (sli < slice)
        {
            // Compute slice angles, cosinus and sinus
            sliceAngle = 2.0 * Math.PI * sli.toDouble() / slice - Math.PI
            sliceAngleFuture = 2.0 * Math.PI * (sli + 1).toDouble() / slice - Math.PI
            //
            cosSliceAngle = cos(sliceAngle)
            cosSliceAngleFuture = cos(sliceAngleFuture)
            sinSliceAngle = sin(sliceAngle)
            sinSliceAngleFuture = sin(sliceAngleFuture)

            // Computes U (Slice walk throw U)
            uA = 1 - multiplierU + multiplierU * sli / slice
            uF = 1 - multiplierU + multiplierU * (sli + 1) / slice

            // For each stack
            sta = 0

            while (sta < stack)
            {
                // Compute stack angles, cosinus and sinus
                stackAngle = PI * sta / stack - Math.PI / 2.0
                stackAngleFuture = PI * (sta + 1) / stack - Math.PI / 2.0
                //
                cosStackAngle = cos(stackAngle)
                cosStackAngleFuture = cos(stackAngleFuture)
                sinStackAngle = sin(stackAngle)
                sinStackAngleFuture = sin(stackAngleFuture)

                // Computes V (Stack walk throw V)
                vA = 1 - multiplierV * sta / stack
                vF = 1 - multiplierV * (sta + 1) / stack

                // Computes normals
                nxAA = (sinSliceAngle * cosStackAngle).toFloat()
                nyAA = sinStackAngle.toFloat()
                nzAA = (cosSliceAngle * cosStackAngle).toFloat()

                nxFA = (sinSliceAngleFuture * cosStackAngle).toFloat()
                nyFA = sinStackAngle.toFloat()
                nzFA = (cosSliceAngleFuture * cosStackAngle).toFloat()

                nxAF = (sinSliceAngle * cosStackAngleFuture).toFloat()
                nyAF = sinStackAngleFuture.toFloat()
                nzAF = (cosSliceAngle * cosStackAngleFuture).toFloat()

                nxFF = (sinSliceAngleFuture * cosStackAngleFuture).toFloat()
                nyFF = sinStackAngleFuture.toFloat()
                nzFF = (cosSliceAngleFuture * cosStackAngleFuture).toFloat()

                this.addSquare(nxFA, nyFA, nzFA, uF, vA,
                               nxFF, nyFF, nzFF, uF, vF,
                               nxAF, nyAF, nzAF, uA, vF,
                               nxAA, nyAA, nzAA, uA, vA)

                sta++
            }

            sli++
        }

        this.seal()
    }
}