package fr.jhelp.compose.engine.loaders.objLoader

import android.util.Log
import fr.jhelp.compose.engine.loaders.NodeLoader
import fr.jhelp.compose.engine.scene.Node3D
import fr.jhelp.compose.engine.scene.Object3D
import fr.jhelp.android.library.math.Point2D
import fr.jhelp.android.library.math.Point3D
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.util.regex.Pattern

private var SPACES = Pattern.compile("\\s+")

/**
 * Loader of file in `obj` format
 */
object ObjLoader : NodeLoader
{
    /**
     * Load nodes and object inside the stream
     *
     * @param name Name to give to the node that will contains all nodes and objects
     * @param inputStream Stream to parse
     * @param seal Indicates if seal extracted object
     * @return Nodes with inside the stream content
     */
    override fun load(name: String, inputStream: InputStream, seal: Boolean): Node3D
    {
        val objRoot = Node3D()
        objRoot.name = name
        val description = this.createObjDescription(name, inputStream)

        for (objObject in description.objects)
        {
            val object3D = Object3D()
            object3D.name = objObject.name
            this.fillMesh(object3D, objObject)

            if (seal)
            {
                object3D.seal()
            }

            objRoot.add(object3D)
        }

        return objRoot
    }

    private fun createObjDescription(name: String,
                                     inputStream: InputStream): ObjDescription
    {
        val objDescription = ObjDescription()
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))

        try
        {
            var line = bufferedReader.readLine()
                ?.trim()
            var objObject = ObjObject("")

            while (line != null)
            {
                when
                {
                    line.startsWith("o ")      ->
                    {
                        if (objObject.name.isNotEmpty())
                        {
                            if (objObject.uvs.isEmpty())
                            {
                                objObject.uvs.add(Point2D())
                            }

                            objDescription.objects.add(objObject)
                        }

                        objObject = ObjObject(line.substring(2)
                                                  .trim())
                    }

                    line.startsWith("usemtl ") ->
                    {
                        if (objObject.name.isNotEmpty())
                        {
                            if (objObject.uvs.isEmpty())
                            {
                                objObject.uvs.add(Point2D())
                            }

                            objDescription.objects.add(objObject)
                        }

                        objObject = objObject.subObject(line.substring(7)
                                                            .trim())
                    }

                    line.startsWith("v ")      ->
                    {
                        val coordinates = line.substring(2)
                            .trim()
                            .split(SPACES)
                            .map { text -> text.toFloat() }
                        objObject.points.add(Point3D(coordinates[0],
                                                     coordinates[1],
                                                     coordinates[2]))
                    }

                    line.startsWith("vt ")     ->
                    {
                        val coordinates = line.substring(3)
                            .trim()
                            .split(SPACES)
                            .map { text -> text.toFloat() }
                        objObject.uvs.add(Point2D(coordinates[0], 1f - coordinates[1]))
                    }

                    line.startsWith("f ")      ->
                    {
                        val objFace = ObjFace()

                        for (vertex in line.substring(2)
                            .trim()
                            .split(SPACES))
                        {
                            val indexes = vertex.split('/')

                            val point = indexes[0].toInt() - 1
                            var uv = 0

                            if (indexes.size > 1)
                            {
                                if (indexes[1].isNotEmpty())
                                {
                                    uv = indexes[1].toInt() - 1
                                }
                            }

                            objFace.vertexs.add(ObjVertex(point, uv))
                        }

                        objObject.faces.add(objFace)
                    }
                }

                line = bufferedReader.readLine()
                    ?.trim()
            }

            if (objObject.name.isNotEmpty())
            {
                if (objObject.uvs.isEmpty())
                {
                    objObject.uvs.add(Point2D())
                }

                objDescription.objects.add(objObject)
            }
        }
        catch (exception: Exception)
        {
            Log.e("ObjLoader", "Failed to load $name", exception)
        }
        finally
        {
            bufferedReader.close()
        }

        return objDescription
    }

    private fun fillMesh(object3d: Object3D, objObject: ObjObject)
    {
        for (face in objObject.faces)
        {
            val size = face.vertexs.size

            if (size < 3)
            {
                continue
            }

            var index = 0

            val start = face.vertexs[index]
            index++
            val startPoint = objObject.points[start.point]
            val startUV = objObject.uvs[start.uv]

            val current = face.vertexs[index]
            index++
            var currentPoint = objObject.points[current.point]
            var currentUV = objObject.uvs[current.uv]

            while (index < size)
            {
                val next = face.vertexs[index]
                index++
                val nextPoint = objObject.points[next.point]
                val nextUV = objObject.uvs[next.uv]

                object3d.addTriangle(startPoint.x, startPoint.y, startPoint.z,
                                     startUV.x, startUV.y,
                                     currentPoint.x, currentPoint.y, currentPoint.z,
                                     currentUV.x, currentUV.y,
                                     nextPoint.x, nextPoint.y, nextPoint.z,
                                     nextUV.x, nextUV.y)

                currentPoint = nextPoint
                currentUV = nextUV
            }
        }
    }
}