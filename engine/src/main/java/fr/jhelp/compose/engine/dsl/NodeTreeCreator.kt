package fr.jhelp.compose.engine.dsl

import android.util.Log
import androidx.annotation.RawRes
import fr.jhelp.compose.engine.loaders.NodeLoader
import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.compose.engine.scene.Clone3D
import fr.jhelp.compose.engine.scene.Node3D
import fr.jhelp.compose.engine.scene.Object3D
import fr.jhelp.compose.engine.scene.geometry.Box
import fr.jhelp.compose.engine.scene.geometry.Plane
import fr.jhelp.compose.engine.scene.geometry.Sphere
import fr.jhelp.tasks.TaskType
import fr.jhelp.tasks.extensions.parallel
import java.io.InputStream

class NodeTreeCreator internal constructor(private val root: Node3D)
{
    fun node(reference: NodeReference = junkReference, node: Node3D.() -> Unit)
    {
        val node3D = Node3D()
        reference.node = node3D
        node(node3D)
        this.root.add(node3D)
    }

    fun object3D(reference: NodeReference = junkReference,
                 object3D: Object3D.() -> Unit)
    {
        val object3DReal = Object3D()
        reference.node = object3DReal
        object3D(object3DReal)
        this.root.add(object3DReal)
    }

    fun clone(reference: NodeReference = junkReference,
              referenceObjectOrClone: NodeReference,
              clone3D: Clone3D.() -> Unit)
    {
        val node = referenceObjectOrClone.node
        val clone =
            when (node)
            {
                is Object3D -> Clone3D(node)
                is Clone3D  -> Clone3D(node.object3D)
                else        -> throw IllegalArgumentException("referenceObjectOrClone not reference to clone or objet 3D, but to ${node::class.java.name}")
            }

        reference.node = clone
        clone3D(clone)
        this.root.add(clone)
    }

    fun plane(reference: NodeReference = junkReference,
              startU: Float = 0f, endU: Float = 1f,
              startV: Float = 0f, endV: Float = 1f,
              plane: Plane.() -> Unit)
    {
        val planeReal = Plane(startU, endU, startV, endV)
        reference.node = planeReal
        plane(planeReal)
        this.root.add(planeReal)
    }

    fun box(reference: NodeReference = junkReference,
            boxUV: BoxUVCreator.() -> Unit = {},
            box: Box.() -> Unit)
    {
        val boxUVCreator = BoxUVCreator()
        boxUV(boxUVCreator)
        val boxReal = Box(boxUVCreator.boxUV)
        reference.node = boxReal
        box(boxReal)
        this.root.add(boxReal)
    }

    fun sphere(reference: NodeReference = junkReference,
               multiplierU: Float = 1f, multiplierV: Float = 1f, slice: Int = 16, slack: Int = 16,
               sphere: Sphere.() -> Unit)
    {
        val sphereReal = Sphere(multiplierU, multiplierV, slice, slack)
        reference.node = sphereReal
        sphere(sphereReal)
        this.root.add(sphereReal)
    }

    fun load(reference: NodeReference = junkReference,
             name: String, @RawRes rawID: Int, loader: NodeLoader,
             material: MaterialReference = MaterialReference(),
             node: Node3D.() -> Unit)
    {
        val nodeParent = Node3D()
        reference.node = nodeParent
        ResourcesAccess.loadNode(name, rawID, loader)
            .and { nodeLoaded ->
                nodeLoaded.applyMaterialHierarchically(material.material)
                nodeParent.add(nodeLoaded)
                Log.d("NodeTreeCreator", "$name loaded !")
            }
        node(nodeParent)
        this.root.add(nodeParent)
    }

    fun load(reference: NodeReference = junkReference,
             name: String, assetPath: String, loader: NodeLoader,
             material: MaterialReference = MaterialReference(),
             node: Node3D.() -> Unit)
    {
        val nodeParent = Node3D()
        reference.node = nodeParent
        ResourcesAccess.loadNode(name, assetPath, loader)
            .and { nodeLoaded ->
                nodeLoaded.applyMaterialHierarchically(material.material)
                nodeParent.add(nodeLoaded)
                Log.d("NodeTreeCreator", "$name loaded !")
            }
        node(nodeParent)
        this.root.add(nodeParent)
    }

    fun load(reference: NodeReference = junkReference,
             name: String, inputStream: InputStream, loader: NodeLoader,
             material: MaterialReference = MaterialReference(),
             node: Node3D.() -> Unit)
    {
        val nodeParent = Node3D()
        reference.node = nodeParent
        ({ loader.load(name, inputStream, true) }).parallel(TaskType.IO)
            .and { nodeLoaded ->
                nodeLoaded.applyMaterialHierarchically(material.material)
                nodeParent.add(nodeLoaded)
                Log.d("NodeTreeCreator", "$name loaded !")
            }
        node(nodeParent)
        this.root.add(nodeParent)
    }
}