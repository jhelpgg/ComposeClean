package fr.jhelp.compose.engine.scene.geometry

import fr.jhelp.compose.engine.scene.Object3D
import fr.jhelp.compose.math.extensions.bounds
import fr.jhelp.compose.math.extensions.same
import fr.jhelp.compose.math.formal.Constant
import fr.jhelp.compose.math.formal.MathFunction
import fr.jhelp.compose.math.formal.Variable
import fr.jhelp.compose.math.formal.X
import fr.jhelp.compose.math.formal.Y
import java.util.TreeSet

/**
 * Filed 3D represents a 3D equation like Z=f(X, Y)
 *
 * The equation must depends on [X] and/or [Y] and nothing else
 */
class Field3D(functionZ: MathFunction<*>,
              private val xStart: Float, private val xEnd: Float, numberPartX: Int,
              private val yStart: Float, private val yEnd: Float, numberPartY: Int,
              private val uStart: Float = 0f, private val uEnd: Float = 1f,
              private val vStart: Float = 0f, private val vEnd: Float = 1f) : Object3D()
{
    private val functionZ = this.checkAndSimplify(functionZ)
    private val numberPartX = numberPartX.bounds(1, 10)
    private val numberPartY = numberPartY.bounds(1, 10)

    init
    {
        this.doubleFace = true
        this.compute()
    }

    fun z(x: Float, y: Float) =
        (this.functionZ.replace(X, x).replace(Y, y).simplifyMax() as Constant).value.toFloat()

    private fun compute()
    {
        if (this.xStart.same(this.xEnd) || this.yStart.same(this.yEnd))
        {
            return
        }

        val xStep = (this.xEnd - this.xStart) / this.numberPartX
        val yStep = (this.yEnd - this.yStart) / this.numberPartY
        val uStep = (this.uEnd - this.uStart) / this.numberPartX
        val vStep = (this.vEnd - this.vStart) / this.numberPartY
        var x1: Float
        var y1: Float
        var x2: Float
        var y2: Float
        var u1: Float
        var u2: Float
        var v1: Float
        var v2: Float

        for (y in 0 until this.numberPartY)
        {
            y1 = this.yStart + y * yStep
            y2 = this.yStart + (y + 1) * yStep
            v1 = this.vStart + y * vStep
            v2 = this.vStart + (y + 1) * vStep

            for (x in 0 until this.numberPartX)
            {
                x1 = this.xStart + x * xStep
                x2 = this.xStart + (x + 1) * yStep
                u1 = this.uStart + x * uStep
                u2 = this.uStart + (x + 1) * uStep

                this.addSquare(x1, y1, this.z(x1, y1), u1, v1,
                               x1, y2, this.z(x1, y2), u1, v2,
                               x2, y2, this.z(x2, y2), u2, v2,
                               x2, y1, this.z(x2, y1), u2, v1)
            }
        }

        this.seal()
        val center = this.center()
        this.position.position(-center.x, -center.y, -center.z)
    }

    private fun checkAndSimplify(function: MathFunction<*>): MathFunction<*>
    {
        val simplify = function.simplifyMax()
        val collector = TreeSet<Variable>()
        simplify.collectVariables(collector)

        return when (collector.size)
        {
            0    -> simplify
            1    ->
                if (X in collector || Y in collector) simplify
                else throw IllegalArgumentException("function '$function' not depends on X or Y")
            2    ->
                if (X in collector && Y in collector) simplify
                else throw IllegalArgumentException(
                    "function '$function' not depends on only X and Y")
            else -> throw IllegalArgumentException(
                "function '$function' have more than two variables")
        }
    }

}