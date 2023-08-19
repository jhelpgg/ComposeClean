package fr.jhelp.android.library.engine.dsl

import android.util.Log
import androidx.annotation.RawRes
import fr.jhelp.android.library.engine.loaders.NodeLoader
import fr.jhelp.android.library.engine.resources.ResourcesAccess
import fr.jhelp.android.library.engine.scene.Clone3D
import fr.jhelp.android.library.engine.scene.Node3D
import fr.jhelp.android.library.engine.scene.Object3D
import fr.jhelp.android.library.engine.scene.geometry.Box
import fr.jhelp.android.library.engine.scene.geometry.Field3D
import fr.jhelp.android.library.engine.scene.geometry.ObjectPath
import fr.jhelp.android.library.engine.scene.geometry.Plane
import fr.jhelp.android.library.engine.scene.geometry.Revolution
import fr.jhelp.android.library.engine.scene.geometry.Sphere
import fr.jhelp.android.library.engine.scene.geometry.dice.Dice
import fr.jhelp.android.library.images.path.Path
import fr.jhelp.android.library.math.formal.MathFunction
import fr.jhelp.android.library.tasks.TaskType
import fr.jhelp.android.library.tasks.extensions.parallel
import java.io.InputStream

/**
 * Create a node tree
 */
class NodeTreeCreator internal constructor(private val root: Node3D)
{
    /**
     * Add node to the node tree
     */
    fun node(reference: NodeReference = junkReference, node: Node3D.() -> Unit)
    {
        val node3D = Node3D()
        reference.node = node3D
        node(node3D)
        this.root.add(node3D)
    }

    /**
     * Add an object 3D to the tree
     */
    fun object3D(reference: NodeReference = junkReference,
                 object3D: Object3D.() -> Unit)
    {
        val object3DReal = Object3D()
        reference.node = object3DReal
        object3D(object3DReal)
        this.root.add(object3DReal)
    }

    /**
     * Add a clone to the node tree
     */
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

    /**
     * Add plane to the node tree
     */
    fun plane(reference: NodeReference = junkReference,
              startU: Float = 0f, endU: Float = 1f,
              startV: Float = 0f, endV: Float = 1f,
              seal: Boolean = true,
              plane: Plane.() -> Unit)
    {
        val planeReal = Plane(startU, endU, startV, endV, seal)
        reference.node = planeReal
        plane(planeReal)
        this.root.add(planeReal)
    }

    /**
     * Add a box to the node tree
     */
    fun box(reference: NodeReference = junkReference,
            boxUV: BoxUVCreator.() -> Unit = {},
            seal: Boolean = true,
            box: Box.() -> Unit)
    {
        val boxUVCreator = BoxUVCreator()
        boxUV(boxUVCreator)
        val boxReal = Box(boxUVCreator.boxUV, seal)
        reference.node = boxReal
        box(boxReal)
        this.root.add(boxReal)
    }

    /**
     * Add a revolution
     */
    fun revolution(reference: NodeReference = junkReference,
                   angle: Float = 360f,
                   multiplierU: Float = 1f, startV: Float = 0f, endV: Float = 1f,
                   pathPrecision: Int = 5, rotationPrecision: Int = 12,
                   path: Path.() -> Unit,
                   seal: Boolean = true,
                   revolution: Revolution.() -> Unit)
    {
        val pathReal = Path()
        path(pathReal)
        val revolutionReal = Revolution(pathReal,
                                        angle,
                                        multiplierU, startV, endV,
                                        pathPrecision, rotationPrecision,
                                        seal)
        reference.node = revolutionReal
        revolution(revolutionReal)
        this.root.add(revolutionReal)
    }

    /**
     * Add a sphere to the node tree
     */
    fun sphere(reference: NodeReference = junkReference,
               multiplierU: Float = 1f, multiplierV: Float = 1f, slice: Int = 16, slack: Int = 16,
               seal: Boolean = true,
               sphere: Sphere.() -> Unit)
    {
        val sphereReal = Sphere(multiplierU, multiplierV, slice, slack, seal)
        reference.node = sphereReal
        sphere(sphereReal)
        this.root.add(sphereReal)
    }

    /** Create object that represents a path along an other one */
    fun objectPath(reference: NodeReference = junkReference,
                   mainPath: Path.() -> Unit, mainPathPrecision: Int = 5,
                   followPath: Path.() -> Unit, followPathPrecision: Int = 5,
                   startU: Float = 0f, endU: Float = 1f,
                   startV: Float = 0f, endV: Float = 1f,
                   seal: Boolean = true,
                   objectPath: ObjectPath.() -> Unit)
    {
        val mainPathReal = Path()
        mainPath(mainPathReal)
        val followPathReal = Path()
        followPath(followPathReal)
        val objectPathReal = ObjectPath(mainPathReal, mainPathPrecision,
                                        followPathReal, followPathPrecision,
                                        startU, endU,
                                        startV, endV,
                                        seal)
        reference.node = objectPathReal
        objectPath(objectPathReal)
        this.root.add(objectPathReal)
    }

    /**
     * Add a dice
     */
    fun dice(diceReference: DiceReference,
             dice: Dice.() -> Unit)
    {
        dice(diceReference.dice)
        this.root.add(diceReference.dice)
    }

    /**
     * Load a node hierarchy from raw resource and add it to the node tree
     */
    fun load(reference: NodeReference = junkReference,
             name: String, @RawRes rawID: Int, loader: NodeLoader,
             material: MaterialReference = MaterialReference(),
             seal: Boolean = true,
             node: Node3D.() -> Unit)
    {
        val nodeParent = Node3D()
        reference.node = nodeParent
        ResourcesAccess.loadNode(name, rawID, loader, seal)
            .and { nodeLoaded ->
                nodeLoaded.applyMaterialHierarchically(material.material)
                nodeParent.add(nodeLoaded)
                Log.d("NodeTreeCreator", "$name loaded !")
            }
        node(nodeParent)
        this.root.add(nodeParent)
    }

    /**
     * Add a 3D filed
     */
    fun field(reference: NodeReference = junkReference,
              functionZ: MathFunction<*>,
              xStart: Float, xEnd: Float, numberPartX: Int,
              yStart: Float, yEnd: Float, numberPartY: Int,
              uStart: Float = 0f, uEnd: Float = 1f,
              vStart: Float = 0f, vEnd: Float = 1f,
              seal: Boolean = true,
              field: Field3D.() -> Unit)
    {
        val fieldReal = Field3D(functionZ,
                                xStart, xEnd, numberPartX,
                                yStart, yEnd, numberPartY,
                                uStart, uEnd,
                                vStart, vEnd,
                                seal)
        reference.node = fieldReal
        field(fieldReal)
        this.root.add(fieldReal)
    }

    /**
     * Load a node hierarchy from assets and add it to the node tree
     */
    fun load(reference: NodeReference = junkReference,
             name: String, assetPath: String, loader: NodeLoader,
             material: MaterialReference = MaterialReference(),
             seal: Boolean = true,
             node: Node3D.() -> Unit)
    {
        val nodeParent = Node3D()
        reference.node = nodeParent
        ResourcesAccess.loadNode(name, assetPath, loader, seal)
            .and { nodeLoaded ->
                nodeLoaded.applyMaterialHierarchically(material.material)
                nodeParent.add(nodeLoaded)
                Log.d("NodeTreeCreator", "$name loaded !")
            }
        node(nodeParent)
        this.root.add(nodeParent)
    }

    /**
     * Load a node hierarchy from a stream and add it to the node tree
     */
    fun load(reference: NodeReference = junkReference,
             name: String, inputStream: InputStream, loader: NodeLoader,
             material: MaterialReference = MaterialReference(),
             seal: Boolean = true,
             node: Node3D.() -> Unit)
    {
        val nodeParent = Node3D()
        reference.node = nodeParent
        ({ loader.load(name, inputStream, seal) }).parallel(TaskType.IO)
            .and { nodeLoaded ->
                nodeLoaded.applyMaterialHierarchically(material.material)
                nodeParent.add(nodeLoaded)
                Log.d("NodeTreeCreator", "$name loaded !")
            }
        node(nodeParent)
        this.root.add(nodeParent)
    }
}