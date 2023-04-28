package fr.jhelp.compose.engine.scene

import fr.jhelp.compose.engine.annotations.OpenGLThread
import fr.jhelp.compose.math.Point3D
import java.util.Stack
import java.util.concurrent.atomic.AtomicInteger
import javax.microedition.khronos.opengles.GL10

open class Node3D : Iterable<Node3D>
{
    companion object
    {
        private val NEXT_ID = AtomicInteger(0)
    }

    /**Node name*/
    var name = ""
    /**Node unique ID*/
    val id = Node3D.NEXT_ID.incrementAndGet()
    private val children = ArrayList<Node3D>()
    /**Node position relative to it parent*/
    var position = Position3D()
    /**Node parent*/
    var parent: Node3D? = null
        private set
    /**Current Z*/
    internal var zOrder = 0f
    var visible = true
    var sound3D : Sound3D? = null

    /**
     * Node center
     */
    open fun center() = Point3D(this.position.x, this.position.y, this.position.z)

    @OpenGLThread
    internal open fun render(gl: GL10) = Unit

    protected open fun internalCopy(): Node3D = Node3D()

    internal open fun hasSomethingToDraw() = false

    /**
     * Node's root parent
     */
    fun relativeRoot(): Node3D
    {
        var node = this

        while (node.parent != null)
        {
            node = node.parent!!
        }

        return node
    }

    fun copy(): Node3D
    {
        val copy = this.internalCopy()
        copy.position = this.position.copy()

        for (child in this.children)
        {
            copy.add(child.copy())
        }

        return copy
    }

    /**
     * Add node as children
     */
    fun add(node: Node3D)
    {
        if (node.parent != null)
        {
            throw IllegalStateException("The node $node is already attached to ${node.parent}")
        }

        node.parent = this

        synchronized(this.children)
        {
            this.children.add(node)
        }
    }

    /**
     * Remove a node child
     */
    fun remove(node: Node3D)
    {
        if (node.parent != this)
        {
            return
        }

        node.parent = null

        synchronized(this.children)
        {
            this.children.remove(node)
        }
    }

    /**
     * Remove all children
     */
    fun removeAllChildren()
    {
        synchronized(this.children)
        {
            for (child in this.children)
            {
                child.parent = null
            }

            this.children.clear()
        }
    }

    protected open fun material(material: Material) = Unit

    /**
     * Apply given material to all it's hierarchy
     */
    fun applyMaterialHierarchically(material: Material)
    {
        val stack = Stack<Node3D>()
        stack.push(this)
        var node: Node3D

        while (stack.isNotEmpty())
        {
            node = stack.pop()
            node.material(material)

            for (child in node.children)
            {
                stack.push(child)
            }
        }
    }

    operator fun plus(node3D: Node3D): Node3D
    {
        this.add(node3D)
        return this
    }

    override fun iterator(): Iterator<Node3D> =
        this.children.iterator()

    override fun toString(): String =
        "${if (this.name.isNotEmpty()) "${this.name} : " else ""}${this.javaClass.simpleName} : ${this.id}"
}