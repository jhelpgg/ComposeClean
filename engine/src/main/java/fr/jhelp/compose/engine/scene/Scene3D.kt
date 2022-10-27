package fr.jhelp.compose.engine.scene

import fr.jhelp.compose.animations.Animation
import fr.jhelp.compose.animations.AnimationFunction
import fr.jhelp.compose.collection.SortedArray
import fr.jhelp.compose.engine.animation.effect.ParticleEffect
import fr.jhelp.compose.engine.annotations.OpenGLThread
import fr.jhelp.compose.engine.math.antiProjection
import fr.jhelp.compose.math.Point3D
import java.util.Stack
import javax.microedition.khronos.opengles.GL10
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Scene is the scene graph, any attached (directly or indirectly) object are drawn
 */
class Scene3D
{
    /** Coroutine scope to launch task in parallel */
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val animations = ArrayList<Animation>()
    private val particleEffects = ArrayList<ParticleEffect>()

    /**
     * Scene background color.
     *
     * The alpha part is ignored
     */
    var backgroundColor = WHITE

    /**
     * Main node of the scene
     */
    var root = Node3D()

    /**
     * Launch an animation
     */
    fun play(animation: Animation)
    {
        this.scope.launch {
            animation.start()

            synchronized(this@Scene3D.animations) {
                this@Scene3D.animations.add(animation)
            }
        }
    }

    /**
     * Launch an animation based on task
     */
    fun play(animation: (Float) -> Boolean): Animation
    {
        val animationFunction = AnimationFunction(animation)
        this.play(animationFunction)
        return animationFunction
    }

    fun play(particleEffect: ParticleEffect)
    {
        this.scope.launch {
            particleEffect.start()

            synchronized(this@Scene3D.particleEffects)
            {
                this@Scene3D.particleEffects.add(particleEffect)
            }
        }
    }

    /**
     * Stop an animation
     */
    fun stop(animation: Animation)
    {
        this.scope.launch {
            synchronized(this@Scene3D.animations)
            {
                if (this@Scene3D.animations.remove(animation))
                {
                    animation.finished()
                }
            }
        }
    }

    fun stop(particleEffect: ParticleEffect)
    {
        this.scope.launch {
            synchronized(this@Scene3D.particleEffects)
            {
                if (this@Scene3D.particleEffects.remove(particleEffect))
                {
                    particleEffect.stop()
                }
            }
        }
    }

    /**
     * Draw the scene
     */
    @OpenGLThread
    internal fun render(gl: GL10)
    {
        gl.glClearColor(this.backgroundColor.red,
                        this.backgroundColor.green,
                        this.backgroundColor.blue,
                        1f)

        val nodes = SortedArray(NodeOrderZ)
        val stack = Stack<Node3D>()
        stack.push(this.root)
        var current: Node3D

        while (stack.isNotEmpty())
        {
            current = stack.pop()

            if (current.visible)
            {
                if (current.hasSomethingToDraw())
                {
                    current.zOrder = antiProjection(current, current.center()).z
                    nodes.add(current)
                }

                for (child in current)
                {
                    stack.push(child)
                }
            }
        }

        for (node in nodes)
        {
            var explore: Node3D? = node

            while (explore != null)
            {
                stack.push(explore)
                explore = explore.parent
            }

            var position = Point3D()

            gl.glPushMatrix()

            while (stack.isNotEmpty())
            {
                val nodeForPosition = stack.pop()
                position = nodeForPosition.position.projection(position)
                nodeForPosition.position.locate(gl)
            }

            node.render(gl)
            gl.glPopMatrix()

            node.sound3D?.position(position.x, position.y, position.z)
        }

        synchronized(this.animations) {
            for (index in this.animations.size - 1 downTo 0)
            {
                val animation = this.animations[index]

                if (!animation.animate())
                {
                    this.animations.removeAt(index)
                    this.scope.launch { animation.finished() }
                }
            }
        }

        gl.glDisable(GL10.GL_DEPTH_TEST)

        synchronized(this.particleEffects) {
            for (index in this.particleEffects.size - 1 downTo 0)
            {
                val particleEffect = this.particleEffects[index]
                val alive = particleEffect.update()
                particleEffect.render(gl)

                if (!alive)
                {
                    this.particleEffects.removeAt(index)
                }
            }
        }

        gl.glEnable(GL10.GL_DEPTH_TEST)

        // DeleteTexture.freeNext(gl)
    }

    operator fun Node3D.unaryPlus() = this@Scene3D.root.add(this)

    /**
     * Search node by it's ID
     */
    fun nodeById(id: Int): Node3D?
    {
        val stack = Stack<Node3D>()
        stack.push(this.root)
        var current: Node3D

        while (stack.isNotEmpty())
        {
            current = stack.pop()

            if (current.id == id)
            {
                return current
            }

            for (child in current)
            {
                stack.push(child)
            }
        }

        return null
    }

    /**
     * Search node by name. If two or more nodes have the requested name, it gives the first found
     */
    fun firstNodeByName(name: String): Node3D?
    {
        val stack = Stack<Node3D>()
        stack.push(this.root)
        var current: Node3D

        while (stack.isNotEmpty())
        {
            current = stack.pop()

            if (current.name == name)
            {
                return current
            }

            for (child in current)
            {
                stack.push(child)
            }
        }

        return null
    }

    /**
     * Get all nodes with given name
     */
    fun allNodesWithName(name: String): List<Node3D>
    {
        val list = ArrayList<Node3D>()
        val stack = Stack<Node3D>()
        stack.push(this.root)
        var current: Node3D

        while (stack.isNotEmpty())
        {
            current = stack.pop()

            if (current.name == name)
            {
                list.add(current)
            }

            for (child in current)
            {
                stack.push(child)
            }
        }

        return list
    }
}