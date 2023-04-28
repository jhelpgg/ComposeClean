package fr.jhelp.compose.engine.dsl

import fr.jhelp.compose.engine.scene.geometry.BoxUV
import fr.jhelp.compose.engine.scene.geometry.CrossUV
import fr.jhelp.compose.engine.scene.geometry.FaceUV

/**
 * Create a box UV. That is to say way texture is applied to a box
 */
class BoxUVCreator
{
    /** Current box UV */
    var boxUV : BoxUV = BoxUV()
        private set

    /**
     * Create a cross texture UV
     */
    fun cross(u1: Float = 1f / 3f, u2: Float = 2f / 3f,
              v1: Float = 0.25f, v2: Float = 0.5f, v3: Float = 0.75f)
    {
        this.boxUV = CrossUV(u1, u2, v1, v2, v3)
    }

    /**
     * Define texture point for box face
     */
    fun face(minU: Float = 0f, maxU: Float = 1f, minV: Float = 0f, maxV: Float = 1f)
    {
        this.boxUV = BoxUV(FaceUV(minU, maxU, minV, maxV), this.boxUV.back,
                           this.boxUV.top, this.boxUV.bottom,
                           this.boxUV.left, this.boxUV.right)
    }

    /**
     * Define texture point for box back
     */
    fun back(minU: Float = 0f, maxU: Float = 1f, minV: Float = 0f, maxV: Float = 1f)
    {
        this.boxUV = BoxUV(this.boxUV.face, FaceUV(minU, maxU, minV, maxV),
                           this.boxUV.top, this.boxUV.bottom,
                           this.boxUV.left, this.boxUV.right)
    }

    /**
     * Define texture point for box top
     */
    fun top(minU: Float = 0f, maxU: Float = 1f, minV: Float = 0f, maxV: Float = 1f)
    {
        this.boxUV = BoxUV(this.boxUV.face, this.boxUV.back,
                           FaceUV(minU, maxU, minV, maxV), this.boxUV.bottom,
                           this.boxUV.left, this.boxUV.right)
    }

    /**
     * Define texture point for box bottom
     */
    fun bottom(minU: Float = 0f, maxU: Float = 1f, minV: Float = 0f, maxV: Float = 1f)
    {
        this.boxUV = BoxUV(this.boxUV.face, this.boxUV.back,
                           this.boxUV.top, FaceUV(minU, maxU, minV, maxV),
                           this.boxUV.left, this.boxUV.right)
    }

    /**
     * Define texture point for box left
     */
    fun left(minU: Float = 0f, maxU: Float = 1f, minV: Float = 0f, maxV: Float = 1f)
    {
        this.boxUV = BoxUV(this.boxUV.face, this.boxUV.back,
                           this.boxUV.top, this.boxUV.bottom,
                           FaceUV(minU, maxU, minV, maxV), this.boxUV.right)
    }

    /**
     * Define texture point for box right
     */
    fun right(minU: Float = 0f, maxU: Float = 1f, minV: Float = 0f, maxV: Float = 1f)
    {
        this.boxUV = BoxUV(this.boxUV.face, this.boxUV.back,
                           this.boxUV.top, this.boxUV.bottom,
                           this.boxUV.left, FaceUV(minU, maxU, minV, maxV))
    }
}
