package fr.jhelp.android.library.engine.scene.morphing

import android.util.Log
import fr.jhelp.android.library.engine.animation.keyFrame.AnimationTextureSize
import fr.jhelp.android.library.engine.annotations.OpenGLThread
import fr.jhelp.android.library.engine.buffer.BufferFloat
import fr.jhelp.android.library.engine.buffer.floatBuffer
import fr.jhelp.android.library.engine.resources.draw
import fr.jhelp.android.library.engine.resources.texture
import fr.jhelp.android.library.engine.scene.Node3D
import fr.jhelp.android.library.engine.scene.Object3D
import fr.jhelp.android.library.engine.scene.Texture
import fr.jhelp.android.library.engine.scene.WHITE
import fr.jhelp.android.library.images.alpha
import fr.jhelp.android.library.images.blue
import fr.jhelp.android.library.images.clear
import fr.jhelp.android.library.images.fitSpace
import fr.jhelp.android.library.images.green
import fr.jhelp.android.library.images.limitPart
import fr.jhelp.android.library.images.pixelsOperation
import fr.jhelp.android.library.images.red
import fr.jhelp.android.library.math.COLOR_ORANGE_0500
import fr.jhelp.android.library.math.Triangle3D
import fr.jhelp.android.library.math.extensions.bounds
import fr.jhelp.android.library.math.extensions.same
import fr.jhelp.android.library.tasks.Mutex
import fr.jhelp.android.library.tasks.extensions.parallel
import java.nio.FloatBuffer
import java.util.concurrent.atomic.AtomicReference
import javax.microedition.khronos.opengles.GL10

/**
 * Create a morphing with two not sealed objects
 * And two not sealed textures
 */
class Morphing(source: Object3D, destination: Object3D,
               sourceTexture: Texture =
                   texture(32, 32)
                       .draw { bitmap, _, _ -> bitmap.clear(COLOR_ORANGE_0500) }!!,
               destinationTexture: Texture = sourceTexture,
               morphingTextureSize: AnimationTextureSize = AnimationTextureSize.MEDIUM) : Node3D()
{
    private val morphingTriangles = ArrayList<MorphingTriangle>()
    private var numberTriangles = 0
    private var points = BufferFloat()
    private var uvs = BufferFloat()
    private val sourcePixels = IntArray(morphingTextureSize.size * morphingTextureSize.size)
    private val destinationPixels = IntArray(morphingTextureSize.size * morphingTextureSize.size)
    private val texture = texture(morphingTextureSize.size, morphingTextureSize.size)
    private val doubleFace: Boolean
    private val status = AtomicReference<MorphingStatus>(MorphingStatus.CREATING)
    private val mutex = Mutex()
    private val textureSize = morphingTextureSize.size

    /** Morphing alpha in [[0, 1]] */
    var alpha: Float = 1f
        set(value)
        {
            field = value.bounds(0f, 1f)
        }

    /** Morphing actual percent */
    var percent: Float = 0f
        set(value)
        {
            val oldValue = field
            val newValue = value.bounds(0f, 1f)
            field = newValue

            if (!oldValue.same(newValue))
            {
                this.updatePercent()
            }
        }

    init
    {
        val errorReport = StringBuilder()

        if (source.sealed)
        {
            errorReport.append("Object 3D source is sealed. ")
        }

        if (destination.sealed)
        {
            errorReport.append("Object 3D destination is sealed. ")
        }

        if (sourceTexture.sealed())
        {
            errorReport.append("Texture source is sealed. ")
        }

        if (destinationTexture.sealed())
        {
            errorReport.append("Texture destination is sealed. ")
        }

        if (errorReport.isNotEmpty())
        {
            errorReport.append("Morphing is possible only with not sealed entries. You ca seal them after morphing construction.")
            throw IllegalArgumentException(errorReport.toString())
        }

        this.texture.draw { bitmap, _, _ ->
            bitmap.clear(0)
            bitmap.fitSpace(destinationTexture.bitmap()!!)
        }
        this.texture.bitmap()!!
            .getPixels(this.destinationPixels, 0, this.textureSize,
                       0, 0, this.textureSize, this.textureSize)

        this.texture.draw { bitmap, _, _ ->
            bitmap.clear(0)
            bitmap.fitSpace(sourceTexture.bitmap()!!)
        }
        this.texture.bitmap()!!
            .getPixels(this.sourcePixels, 0, this.textureSize,
                       0, 0, this.textureSize, this.textureSize)

        this.doubleFace = source.doubleFace || destination.doubleFace
        val sourceTriangles = source.triangles()
        val destinationTriangles = destination.triangles()

        ({ this.createMorphing(sourceTriangles, destinationTriangles) }).parallel()
    }

    /**
     * Draw the object in 3D
     */
    @OpenGLThread
    override fun render(gl: GL10)
    {
        var points: FloatBuffer = floatBuffer(1)
        var uvs: FloatBuffer = floatBuffer(1)
        var numberTriangles: Int = 0

        this.mutex {
            points = this.points.buffer()
            uvs = this.uvs.buffer()
            numberTriangles = this.numberTriangles
        }

        gl.glDisable(GL10.GL_TEXTURE_2D)
        gl.glColor4f(1f, 1f, 1f, this.alpha)
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, WHITE.floatBuffer())

        if (this.doubleFace)
        {
            gl.glDisable(GL10.GL_CULL_FACE)
        }
        else
        {
            gl.glEnable(GL10.GL_CULL_FACE)
        }

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, points)
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, uvs)

        for (offset in 0 until numberTriangles * 3 step 3)
        {
            gl.glDrawArrays(GL10.GL_TRIANGLES, offset, 3)
        }

        if (this.status.get() == MorphingStatus.DIRTY)
        {
            this::updatePercentReal.parallel(this.percent)
        }
    }

    private fun updatePercent()
    {
        if (this.status.compareAndSet(MorphingStatus.READY, MorphingStatus.RENDERING))
        {
            this::updatePercentReal.parallel(this.percent)
            return
        }

        this.status.compareAndSet(MorphingStatus.RENDERING, MorphingStatus.DIRTY)
    }

    private fun updatePercentReal(percent: Float)
    {
        val points = BufferFloat()
        val uvs = BufferFloat()
        val numberTriangle = this.morphingTriangles.size

        for (morphingTriangle in this.morphingTriangles)
        {
            val triangle = morphingTriangle.interpolate(percent)
            points.add(triangle.first.point3D.x)
            points.add(triangle.first.point3D.y)
            points.add(triangle.first.point3D.z)
            uvs.add(triangle.first.uv.x)
            uvs.add(triangle.first.uv.y)
            points.add(triangle.second.point3D.x)
            points.add(triangle.second.point3D.y)
            points.add(triangle.second.point3D.z)
            uvs.add(triangle.second.uv.x)
            uvs.add(triangle.second.uv.y)
            points.add(triangle.third.point3D.x)
            points.add(triangle.third.point3D.y)
            points.add(triangle.third.point3D.z)
            uvs.add(triangle.third.uv.x)
            uvs.add(triangle.third.uv.y)
        }

        this.texture.draw { bitmap, _, _ ->
            bitmap.pixelsOperation { pixels ->
                val antiPercent = 1f - percent
                for ((index, sourcePixel) in this.sourcePixels.withIndex())
                {
                    val destinationPixel = this.destinationPixels[index]
                    pixels[index] =
                        (limitPart(sourcePixel.alpha * antiPercent + destinationPixel.alpha * percent) shl 24) or
                                (limitPart(sourcePixel.red * antiPercent + destinationPixel.red * percent) shl 16) or
                                (limitPart(sourcePixel.green * antiPercent + destinationPixel.green * percent) shl 8) or
                                limitPart(sourcePixel.blue * antiPercent + destinationPixel.blue * percent)
                }
            }
        }

        this.mutex {
            this.points = points
            this.uvs = uvs
            this.numberTriangles = numberTriangle
        }

        if (percent == this.percent)
        {
            this.status.set(MorphingStatus.READY)
        }
    }

    private fun createMorphing(sourceTriangles: List<Triangle3D>,
                               destinationTriangles: List<Triangle3D>)
    {
        val source = ArrayList<Triangle3D>(sourceTriangles)
        val destination = ArrayList<Triangle3D>(destinationTriangles)
        this.equilibrateNumberTriangles(source, 0, source.size,
                                        destination, 0, destination.size)

        if (source.size != destination.size)
        {
            Log.e("ERROR",
                  "createMorphing:  Issue while creating morphing source and destination must have same size fater equilibrating. Source size = ${source.size}, destination size = ${destination.size}")
            return
        }

        for ((index, sourceTriangle) in source.withIndex())
        {
            this.morphingTriangles.add(MorphingTriangle(sourceTriangle, destination[index]))
        }

        this.status.set(MorphingStatus.RENDERING)
        this.updatePercentReal(this.percent)
    }

    private fun equilibrateNumberTriangles(
        source: ArrayList<Triangle3D>, sourceStart: Int, sourceEndExclude: Int,
        destination: ArrayList<Triangle3D>, destinationStart: Int, destinationEndExclude: Int)
    {
        val numberSource = sourceEndExclude - sourceStart
        val numberDestination = destinationEndExclude - destinationStart

        if (numberSource == numberDestination || numberSource == 0 || numberDestination == 0)
        {
            return
        }

        if (numberSource == 1)
        {
            val triangles = source[sourceStart].cutInTwo()

            if (MoprphingTriangleComparator.compare(triangles.first, triangles.second) >= 0)
            {
                source[sourceStart] = triangles.first
                source.add(sourceStart, triangles.second)
            }
            else
            {
                source[sourceStart] = triangles.second
                source.add(sourceStart, triangles.first)
            }

            this.equilibrateNumberTriangles(source, sourceStart, sourceEndExclude + 1,
                                            destination, destinationStart, destinationEndExclude)
            return
        }

        if (numberDestination == 1)
        {
            val triangles = destination[destinationStart].cutInTwo()

            if (MoprphingTriangleComparator.compare(triangles.first, triangles.second) >= 0)
            {
                destination[sourceStart] = triangles.first
                destination.add(sourceStart, triangles.second)
            }
            else
            {
                destination[sourceStart] = triangles.second
                destination.add(sourceStart, triangles.first)
            }

            this.equilibrateNumberTriangles(source, sourceStart, sourceEndExclude,
                                            destination, destinationStart,
                                            destinationEndExclude + 1)
            return
        }

        if (numberSource < numberDestination)
        {
            val divide = numberDestination / numberSource
            val remaining = numberDestination % numberSource
            var destinationComputed = destinationEndExclude - divide
            var number = divide

            for (index in numberSource - 1 downTo 0)
            {
                this.equilibrateNumberTriangles(source, sourceStart + index,
                                                sourceStart + index + 1,
                                                destination, destinationComputed,
                                                destinationComputed + number)
                number = divide + (if (index <= remaining) 1 else 0)
                destinationComputed -= number
            }

            return
        }

        val divide = numberSource / numberDestination
        val remaining = numberSource % numberDestination
        var sourceComputed = sourceEndExclude - divide
        var number = divide

        for (index in numberDestination - 1 downTo 0)
        {
            this.equilibrateNumberTriangles(source, sourceComputed,
                                            sourceComputed + number,
                                            destination, destinationStart + index,
                                            destinationStart + index + 1)
            number = divide + (if (index <= remaining) 1 else 0)
            sourceComputed -= number
        }
    }
}
