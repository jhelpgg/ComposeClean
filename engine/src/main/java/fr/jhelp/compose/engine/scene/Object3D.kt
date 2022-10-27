/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.engine.scene

import android.graphics.Paint
import fr.jhelp.compose.engine.annotations.OpenGLThread
import fr.jhelp.compose.engine.buffer.BufferFloat
import fr.jhelp.compose.engine.math.BoundingBox
import fr.jhelp.compose.engine.resources.draw
import fr.jhelp.compose.engine.resources.texture
import fr.jhelp.compose.images.clear
import javax.microedition.khronos.opengles.GL10

/**
 * Object 3D with mesh.
 *
 * The mesh is create with [addTriangle] and/or [addSquare]
 *
 * It can be seal or not. When sealed, the object can't be modified, [addTriangle] or [addSquare] are ignored.
 *
 * Seal optimize memory
 */
open class Object3D : NodeWithBoundingBox()
{
    private val points = BufferFloat()
    private val uvs = BufferFloat()
    private val boundingBox = BoundingBox()
    /**Number of triangles in mesh*/
    var numberTriangle = 0
        private set
    /**Material used to render the object*/
    var material = Material()
    /**Indicates if object is double face*/
    var doubleFace = false
    /**Current seal states*/
    val sealed get() = this.points.sealed

    /**
     * Object current center
     */
    final override fun center() = this.boundingBox.center()

    /**
     * Object current bounding box
     */
    final override fun boundingBox() = this.boundingBox.copy()

    /**
     * Indicates if object has something to draw (at least one triangle)
     */
    final override fun hasSomethingToDraw() = this.numberTriangle > 0

    /**
     * Change material
     */
    final override fun material(material: Material)
    {
        this.material = material
    }

    final override fun internalCopy(): Node3D
    {
        val copy = Clone3D(this)
        copy.material = this.material
        return copy
    }

    /**
     * Seal the object.
     *
     * Will free some memory, but object can't change after call this method
     */
    fun seal()
    {
        this.points.seal()
        this.uvs.seal()
    }

    /**
     * Add a triangle to mesh
     */
    fun addTriangle(x1: Float, y1: Float, z1: Float, u1: Float, v1: Float,
                    x2: Float, y2: Float, z2: Float, u2: Float, v2: Float,
                    x3: Float, y3: Float, z3: Float, u3: Float, v3: Float)
    {
        if (this.sealed)
        {
            return
        }

        this.boundingBox.add(x1, y1, z1)
        this.boundingBox.add(x2, y3, z2)
        this.boundingBox.add(x3, y3, z3)

        this.points.add(x1, y1, z1,
                        x2, y2, z2,
                        x3, y3, z3)
        this.uvs.add(u1, v1,
                     u2, v2,
                     u3, v3)
        this.numberTriangle++
    }

    /**
     * Add square to mesh
     */
    fun addSquare(topLeftX: Float, topLeftY: Float, topLeftZ: Float,
                  topLeftU: Float, topLeftV: Float,
                  bottomLeftX: Float, bottomLeftY: Float, bottomLeftZ: Float,
                  bottomLeftU: Float, bottomLeftV: Float,
                  bottomRightX: Float, bottomRightY: Float, bottomRightZ: Float,
                  bottomRightU: Float, bottomRightV: Float,
                  topRightX: Float, topRightY: Float, topRightZ: Float,
                  topRightU: Float, topRightV: Float)
    {
        if (this.sealed)
        {
            return
        }

        this.boundingBox.add(topLeftX, topLeftY, topLeftZ)
        this.boundingBox.add(bottomLeftX, bottomLeftY, bottomLeftZ)
        this.boundingBox.add(bottomRightX, bottomRightY, bottomRightZ)
        this.boundingBox.add(topRightX, topRightY, topRightZ)

        this.points.add(topLeftX, topLeftY, topLeftZ,
                        bottomLeftX, bottomLeftY, bottomLeftZ,
                        bottomRightX, bottomRightY, bottomRightZ,
            //
                        topLeftX, topLeftY, topLeftZ,
                        bottomRightX, bottomRightY, bottomRightZ,
                        topRightX, topRightY, topRightZ)
        this.uvs.add(topLeftU, topLeftV,
                     bottomLeftU, bottomLeftV,
                     bottomRightU, bottomRightV,
            //
                     topLeftU, topLeftV,
                     bottomRightU, bottomRightV,
                     topRightU, topRightV)
        this.numberTriangle += 2
    }

    /**
     * Create texture that represents the "wire frame" and apply it.
     *
     * Call it before object add to the scene (or a [Node3D])
     */
    fun showWire()
    {
        val texture = texture(512, 512)
            .draw { bitmap, canvas, paint ->
                bitmap.clear(0xFFFFFFFF.toInt())
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 1f
                paint.color = 0xFF000000.toInt()
                var left = this.uvs.size
                val buffer = this.uvs.buffer()
                buffer.position(0)

                while (left > 0)
                {
                    val x1 = 512f * buffer.get()
                    left--

                    if (left <= 0)
                    {
                        break
                    }

                    val y1 = 512f * buffer.get()
                    left--

                    if (left <= 0)
                    {
                        break
                    }

                    val x2 = 512f * buffer.get()
                    left--

                    if (left <= 0)
                    {
                        break
                    }

                    val y2 = 512f * buffer.get()
                    left--

                    if (left <= 0)
                    {
                        break
                    }

                    val x3 = 512f * buffer.get()
                    left--

                    if (left <= 0)
                    {
                        break
                    }

                    val y3 = 512f * buffer.get()
                    left--
                    canvas.drawLine(x1, y1, x2, y2, paint)
                    canvas.drawLine(x2, y2, x3, y3, paint)
                    canvas.drawLine(x3, y3, x1, y1, paint)
                }

                buffer.position(0)
            }
        this.material.diffuse = LIGHT_GREY
        this.material.texture = texture
    }

    /**
     * Draw the object in 3D
     */
    @OpenGLThread
    final override fun render(gl: GL10)
    {
        this.material.render(gl)
        this.draw(gl)
    }

    /**
     * Draw the object in 3D
     */
    @OpenGLThread
    internal fun draw(gl: GL10)
    {
        if (this.doubleFace)
        {
            gl.glDisable(GL10.GL_CULL_FACE)
        }
        else
        {
            gl.glEnable(GL10.GL_CULL_FACE)
        }

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.points.buffer())
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.uvs.buffer())

        for (offset in 0 until this.numberTriangle * 3 step 3)
        {
            gl.glDrawArrays(GL10.GL_TRIANGLES, offset, 3)
        }
    }
}