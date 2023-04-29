package fr.jhelp.compose.engine.scene

import fr.jhelp.compose.animations.Animation
import fr.jhelp.compose.animations.AnimationFunction
import fr.jhelp.compose.collection.SortedArray
import fr.jhelp.compose.engine.animation.effect.ParticleEffect
import fr.jhelp.compose.engine.annotations.OpenGLThread
import fr.jhelp.compose.engine.buffer.BufferFloat
import fr.jhelp.compose.engine.math.antiProjection
import fr.jhelp.compose.engine.view.ViewBounds
import fr.jhelp.compose.math.Point3D
import fr.jhelp.tasks.TaskType
import fr.jhelp.tasks.extensions.observedBy
import java.util.Stack
import javax.microedition.khronos.opengles.GL10
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Scene is the scene graph, any attached (directly or indirectly) object are drawn
 */
class Scene3D internal constructor(viewBoundsState: StateFlow<ViewBounds>)
{
    companion object
    {
        private const val PANE_Z = -1.001f
    }

    /** Coroutine scope to launch task in parallel */
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val animations = ArrayList<Animation>()
    private val particleEffects = ArrayList<ParticleEffect>()
    private val underOver3DPlanePoints = BufferFloat()
    private val underOver3DPlaneUvs = BufferFloat()

    /**
     * Scene background color.
     *
     * The alpha part is ignored
     */
    var backgroundColor: Color3D = WHITE

    /**
     * Main node of the scene
     */
    var root: Node3D = Node3D()

    /** Texture applied on background */
    var textureBackground: Texture? = null

    /** Texture draw over the 3D */
    var textureOver3D: Texture? = null

    init
    {
        val topLeftU = 0f
        val topLeftV = 0f
        val bottomLeftU = 0f
        val bottomLeftV = 1f
        val bottomRightU = 1f
        val bottomRightV = 1f
        val topRightU = 1f
        val topRightV = 0f

        this.underOver3DPlaneUvs.add(topLeftU, topLeftV,
                                     bottomLeftU, bottomLeftV,
                                     bottomRightU, bottomRightV,
            //
                                     topLeftU, topLeftV,
                                     bottomRightU, bottomRightV,
                                     topRightU, topRightV)

        viewBoundsState.observedBy(TaskType.SHORT_TASK, this::boundsChanged)
    }

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

    /**
     * Launch a particle effect
     */
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

    /**
     * Stop a particle effect
     */
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

        this.textureBackground?.let { texture ->
            gl.glDisable(GL10.GL_DEPTH_TEST)
            this.drawTextureOnPlane(gl, texture)
            gl.glEnable(GL10.GL_DEPTH_TEST)
        }

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

        this.textureOver3D?.let { texture -> this.drawTextureOnPlane(gl, texture) }

        // DeleteTexture.freeNext(gl)
    }

    /**
     * Add a node to th scene
     */
    operator fun Node3D.unaryPlus(): Unit = this@Scene3D.root.add(this)

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

    private fun boundsChanged(viewBounds: ViewBounds)
    {
        this.underOver3DPlanePoints.clear()

        val topLeftX = viewBounds.topLeftNear.x
        val topLeftY = viewBounds.topLeftNear.y
        val topLeftZ = Scene3D.PANE_Z
        val bottomLeftX = viewBounds.topLeftNear.x
        val bottomLeftY = viewBounds.bottomRightFar.y
        val bottomLeftZ = Scene3D.PANE_Z
        val bottomRightX = viewBounds.bottomRightFar.x
        val bottomRightY = viewBounds.bottomRightFar.y
        val bottomRightZ = Scene3D.PANE_Z
        val topRightX = viewBounds.bottomRightFar.x
        val topRightY = viewBounds.topLeftNear.y
        val topRightZ = Scene3D.PANE_Z

        this.underOver3DPlanePoints.add(topLeftX, topLeftY, topLeftZ,
                                        bottomLeftX, bottomLeftY, bottomLeftZ,
                                        bottomRightX, bottomRightY, bottomRightZ,
            //
                                        topLeftX, topLeftY, topLeftZ,
                                        bottomRightX, bottomRightY, bottomRightZ,
                                        topRightX, topRightY, topRightZ)

    }

    private fun drawTextureOnPlane(gl: GL10, texture: Texture)
    {
        gl.glPushMatrix()
        gl.glDisable(GL10.GL_TEXTURE_2D)
        gl.glColor4f(WHITE.red, WHITE.green, WHITE.blue, 1.0f)
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, WHITE.floatBuffer())
        gl.glEnable(GL10.GL_CULL_FACE)
        gl.glEnable(GL10.GL_TEXTURE_2D)
        texture.bind(gl)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.underOver3DPlanePoints.buffer())
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, this.underOver3DPlaneUvs.buffer())

        for (offset in 0 until 2 * 3 step 3)
        {
            gl.glDrawArrays(GL10.GL_TRIANGLES, offset, 3)
        }

        gl.glPopMatrix()

    }
}