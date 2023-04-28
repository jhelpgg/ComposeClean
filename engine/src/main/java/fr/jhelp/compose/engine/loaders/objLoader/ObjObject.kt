package fr.jhelp.compose.engine.loaders.objLoader

import fr.jhelp.compose.math.Point2D
import fr.jhelp.compose.math.Point3D

internal class ObjObject(var name : String)
{
    val points = ArrayList<Point3D>()
    val uvs = ArrayList<Point2D>()
    val faces = ArrayList<ObjFace>()

    fun subObject(name:String) : ObjObject {
        val subObject = ObjObject(name)
        subObject.points.addAll(this.points)
        subObject.uvs.addAll(this.uvs)
        return subObject
    }
}
