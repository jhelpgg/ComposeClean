package fr.jhelp.android.library.engine.scene.morphing

import android.util.Log
import fr.jhelp.android.library.engine.animation.keyFrame.AnimationTextureSize
import fr.jhelp.android.library.engine.annotations.OpenGLThread
import fr.jhelp.android.library.engine.buffer.BufferFloat
import fr.jhelp.android.library.engine.buffer.floatBuffer
import fr.jhelp.android.library.engine.resources.draw
import fr.jhelp.android.library.engine.resources.texture
import fr.jhelp.android.library.engine.scene.Color3D
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
               morphingTextureSize: AnimationTextureSize = AnimationTextureSize.MEDIUM,
               scaleStart: Float = 1f,
               scaleEnd: Float = 1f) : Node3D()
{
    private val morphingTriangles = ArrayList<MorphingTriangle>()
    private var numberTriangles = 0
    private var points = BufferFloat()
    private var uvs = BufferFloat()
    private val sourcePixels = IntArray(morphingTextureSize.size * morphingTextureSize.size)
    private val destinationPixels = IntArray(morphingTextureSize.size * morphingTextureSize.size)
    private val texture = texture(morphingTextureSize.size, morphingTextureSize.size)
    private val status = AtomicReference<MorphingStatus>(MorphingStatus.CREATING)
    private val mutex = Mutex()
    private val textureSize = morphingTextureSize.size
    private var diffuse: Color3D = WHITE
    private var alpha: Float = 1f

    /** Indicates if morphing is double face */
    var doubleFace: Boolean

    /** Diffuse color at start */
    var diffuseStart: Color3D = WHITE

    /** Diffuse color at end */
    var diffuseEnd: Color3D = WHITE

    /** Morphing alpha  at start in [[0, 1]] */
    var alphaStart: Float = 1f
        set(value)
        {
            field = value.bounds(0f, 1f)
        }

    /** Morphing alpha  at end in [[0, 1]] */
    var alphaEnd: Float = 1f
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

        ({
            this.createMorphing(sourceTriangles, scaleStart,
                                destinationTriangles, scaleEnd)
        }).parallel()
    }

    /**
     * Indicates if object has something to draw (at least one triangle)
     */
    override fun hasSomethingToDraw(): Boolean = this.numberTriangles > 0

    /**
     * Draw the object in 3D
     */
    @OpenGLThread
    override fun render(gl: GL10)
    {
        var points: FloatBuffer = floatBuffer(1)
        var uvs: FloatBuffer = floatBuffer(1)
        var numberTriangles: Int = 0
        var alpha = 1f
        var diffuse = WHITE

        this.mutex {
            alpha = this.alpha
            diffuse = this.diffuse
            points = this.points.buffer()
            uvs = this.uvs.buffer()
            numberTriangles = this.numberTriangles
        }

        gl.glDisable(GL10.GL_TEXTURE_2D)
        gl.glColor4f(diffuse.red, diffuse.green, diffuse.blue, alpha)
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuse.floatBuffer())
        gl.glEnable(GL10.GL_TEXTURE_2D)
        this.texture.bind(gl)

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

        val antiPercent = 1f - percent

        this.texture.draw { bitmap, _, _ ->
            bitmap.pixelsOperation { pixels ->
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

        val alpha = (this.alphaStart * antiPercent + this.alphaEnd * percent)
        val diffuse = Color3D(this.diffuseStart.red * antiPercent + this.diffuseEnd.red * percent,
                              this.diffuseStart.green * antiPercent + this.diffuseEnd.green * percent,
                              this.diffuseStart.blue * antiPercent + this.diffuseEnd.blue * percent,
                              this.diffuseStart.alpha * antiPercent + this.diffuseEnd.alpha * percent)

        this.mutex {
            this.alpha = alpha
            this.diffuse = diffuse
            this.points = points
            this.uvs = uvs
            this.numberTriangles = numberTriangle
        }

        if (percent == this.percent)
        {
            this.status.set(MorphingStatus.READY)
        }
    }

    private fun createMorphing(sourceTriangles: List<Triangle3D>, scaleSource: Float,
                               destinationTriangles: List<Triangle3D>, scaleDestination: Float)
    {
        val source = ArrayList<Triangle3D>()

        for (triangle in sourceTriangles)
        {
            source.add(triangle.scale(scaleSource))
        }

        val destination = ArrayList<Triangle3D>()

        for (triangle in destinationTriangles)
        {
            destination.add(triangle.scale(scaleDestination))
        }

        this.equilibrateNumberTriangles(source, destination)

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

    private fun equilibrateNumberTriangles(source: ArrayList<Triangle3D>,
                                           destination: ArrayList<Triangle3D>)
    {
        when
        {
            source.size < destination.size -> this.treatSourceSmallerDestination(source,
                                                                                 destination)

            source.size > destination.size -> this.treatSourceBiggerDestination(source, destination)
            else                           -> this.treatSourceAndDestinationSameSize(source,
                                                                                     destination)
        }
    }

    private fun treatSourceAndDestinationSameSize(source: ArrayList<Triangle3D>,
                                                  destination: ArrayList<Triangle3D>)
    {
        val pairs = ArrayList<Pair<Triangle3D, Triangle3D>>()

        for (triangle in source)
        {
            val nearInfo = NearInfo(triangle, destination)
            pairs.add(Pair(triangle, nearInfo.nearestTriangle))
            destination.removeAt(nearInfo.nearestIndex)
        }

        source.clear()
        destination.clear()

        for ((triangleSource, triangleDestination) in pairs)
        {
            source.add(triangleSource)
            destination.add(triangleDestination)
        }
    }

    private fun treatSourceSmallerDestination(source: ArrayList<Triangle3D>,
                                              destination: ArrayList<Triangle3D>)
    {
        val corresponding = ArrayList<Pair<Triangle3D, ArrayList<Triangle3D>>>()

        for (triangle in source)
        {
            corresponding.add(Pair(triangle, ArrayList<Triangle3D>()))
        }

        while (destination.isNotEmpty())
        {
            for ((triangle, list) in corresponding)
            {
                val nearInfo = NearInfo(triangle, destination)
                list.add(nearInfo.nearestTriangle)
                destination.removeAt(nearInfo.nearestIndex)

                if (destination.isEmpty())
                {
                    break
                }
            }
        }

        source.clear()
        destination.clear()

        while (corresponding.isNotEmpty())
        {
            val (triangle, list) = corresponding.removeAt(0)

            if (list.size == 1)
            {
                source.add(triangle)
                destination.add(list[0])
                continue
            }

            val (triangle1, triangle2) = triangle.cutInTwo()
            val list1 = ArrayList<Triangle3D>()
            val list2 = ArrayList<Triangle3D>()

            while (list.isNotEmpty())
            {
                var nearInfo = NearInfo(triangle1, list)
                list1.add(nearInfo.nearestTriangle)
                list.removeAt(nearInfo.nearestIndex)

                if (list.isNotEmpty())
                {
                    nearInfo = NearInfo(triangle2, list)
                    list2.add(nearInfo.nearestTriangle)
                    list.removeAt(nearInfo.nearestIndex)
                }
            }

            corresponding.add(Pair(triangle1, list1))
            corresponding.add(Pair(triangle2, list2))
        }
    }

    private fun treatSourceBiggerDestination(source: ArrayList<Triangle3D>,
                                             destination: ArrayList<Triangle3D>)
    {
        val corresponding = ArrayList<Pair<Triangle3D, ArrayList<Triangle3D>>>()

        for (triangle in destination)
        {
            corresponding.add(Pair(triangle, ArrayList<Triangle3D>()))
        }

        while (source.isNotEmpty())
        {
            for ((triangle, list) in corresponding)
            {
                val nearInfo = NearInfo(triangle, source)
                list.add(nearInfo.nearestTriangle)
                source.removeAt(nearInfo.nearestIndex)

                if (source.isEmpty())
                {
                    break
                }
            }
        }

        destination.clear()
        source.clear()

        while (corresponding.isNotEmpty())
        {
            val (triangle, list) = corresponding.removeAt(0)

            if (list.size == 1)
            {
                destination.add(triangle)
                source.add(list[0])
                continue
            }

            val (triangle1, triangle2) = triangle.cutInTwo()
            val list1 = ArrayList<Triangle3D>()
            val list2 = ArrayList<Triangle3D>()

            while (list.isNotEmpty())
            {
                var nearInfo = NearInfo(triangle1, list)
                list1.add(nearInfo.nearestTriangle)
                list.removeAt(nearInfo.nearestIndex)

                if (list.isNotEmpty())
                {
                    nearInfo = NearInfo(triangle2, list)
                    list2.add(nearInfo.nearestTriangle)
                    list.removeAt(nearInfo.nearestIndex)
                }
            }

            corresponding.add(Pair(triangle1, list1))
            corresponding.add(Pair(triangle2, list2))
        }
    }
}
