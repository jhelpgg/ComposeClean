package fr.jhelp.android.library.showcase.game.models

import fr.jhelp.compose.animations.AnimationList
import fr.jhelp.compose.animations.animationTask
import fr.jhelp.compose.animations.interpolation.AccelerationInterpolation
import fr.jhelp.compose.animations.interpolation.DecelerationInterpolation
import fr.jhelp.android.library.engine.animation.keyFrame.AnimationNode3D
import fr.jhelp.android.library.engine.extensions.position
import fr.jhelp.android.library.engine.resources.ResourcesAccess
import fr.jhelp.android.library.engine.resources.image.ImageSource
import fr.jhelp.android.library.engine.resources.image.ImageSourceAsset
import fr.jhelp.android.library.engine.resources.image.ImageSourceCutWithPath
import fr.jhelp.android.library.engine.resources.texture
import fr.jhelp.android.library.engine.scene.Material
import fr.jhelp.android.library.engine.scene.Node3D
import fr.jhelp.android.library.engine.scene.Position3D
import fr.jhelp.android.library.engine.scene.Scene3D
import fr.jhelp.android.library.engine.scene.Texture
import fr.jhelp.android.library.engine.scene.WHITE
import fr.jhelp.android.library.engine.scene.geometry.ObjectPath
import fr.jhelp.android.library.engine.scene.geometry.Plane
import fr.jhelp.android.library.engine.scene.geometry.Revolution
import fr.jhelp.android.library.images.lighter
import fr.jhelp.android.library.images.path.Path
import fr.jhelp.android.library.math.AngleFloat
import fr.jhelp.android.library.math.AngleUnit
import fr.jhelp.android.library.math.degreeToRadian
import fr.jhelp.android.library.tasks.TaskType
import fr.jhelp.android.library.tasks.extensions.doWhen
import kotlin.math.cos
import kotlin.math.sin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * A castle
 */
object Castle : Model3D()
{
    private val wallImageSource: ImageSource<*> =
        ImageSourceAsset("game/images/textures/WallColorfulD.jpg")
    private val wallTexture: Texture by lazy { texture(this.wallImageSource) }
    private val bridgeTexture: Texture by lazy {
        val texture = ResourcesAccess.obtainTexture("game/images/textures/Wood_Planks.png",
                                                    false)
        texture.bitmap()?.lighter(64)
        texture.refresh()
        texture.seal()
        texture
    }
    private val wallMaterial: Material by lazy {
        val material = Material()
        material.diffuse = WHITE
        material.texture = this.wallTexture
        material
    }
    private val bridgeMaterial: Material by lazy {
        val material = Material()
        material.diffuse = WHITE
        material.texture = this.bridgeTexture
        material
    }
    private val tower: Node3D by lazy { this.creteTower() }
    private val fullWall: Node3D by lazy { this.createFullWall() }
    private val plane: Plane by lazy {
        val plane = Plane()
        plane.material = this.wallMaterial
        plane
    }
    private lateinit var bridge: Node3D
    private val bridgeStateMutable = MutableStateFlow<CastleBridgeState>(CastleBridgeState.CLOSED)

    /** Bridge open state */
    val bridgeState: Flow<CastleBridgeState> = this.bridgeStateMutable.asStateFlow()

    override fun create(): Node3D
    {
        val node = Node3D()

        // Towers

        var tower = this.tower.copy()
        tower.position {
            this.x = -1f
            this.z = -1f
        }
        node.add(tower)

        tower = Castle.tower.copy()
        tower.position {
            this.x = 1f
            this.z = -1f
        }
        node.add(tower)

        tower = Castle.tower.copy()
        tower.position {
            this.x = 1f
            this.z = 1f
        }
        node.add(tower)

        tower = Castle.tower.copy()
        tower.position {
            this.x = -1f
            this.z = 1f
        }
        node.add(tower)

        // Walls

        var wall = this.fullWall.copy()
        wall.position {
            this.z = -1f
        }
        node.add(wall)

        wall = this.fullWall.copy()
        wall.position {
            this.x = -1f
            this.angleY = 90f
        }
        node.add(wall)

        wall = this.fullWall.copy()
        wall.position {
            this.x = 1f
            this.angleY = 90f
        }
        node.add(wall)

        // Face with door

        val door = 0.3f
        val height = 0.6f
        val mainPath = Path()
        mainPath.moveTo(-door, -1f)
        mainPath.lineTo(-1f, -1f)
        mainPath.lineTo(-1f, height)
        mainPath.lineTo(1f, height)
        mainPath.lineTo(1f, -1f)
        mainPath.lineTo(door, -1f)
        mainPath.lineTo(door, -0.2f)
        mainPath.ellipticArcTo(radiusX = door,
                               radiusY = door * 2f / 3f,
                               rotationAxisX = AngleFloat(180f, AngleUnit.DEGREE),
                               largeArc = true,
                               sweep = true,
                               endX = -door,
                               endY = -0.2f)
        mainPath.lineTo(-door, -1f)
        val followPath = Path()
        followPath.moveTo(0f, 0f)
        followPath.lineTo(0f, -0.2f)
        val objectPath = ObjectPath(mainPath = mainPath,
                                    followPath = followPath,
                                    startU = 0f, endU = 0.4f,
                                    startV = 0f, endV = 10f)
        objectPath.material = this.wallMaterial
        objectPath.position {
            this.angleX = 90f
            this.angleY = 180f
            this.z = 1.1f
        }
        node.add(objectPath)

        val texture = texture(ImageSourceCutWithPath(this.wallImageSource, mainPath))
        val materialFace = Material()
        materialFace.diffuse = WHITE
        materialFace.texture = texture

        val faceBack = this.plane.copy()
        faceBack.applyMaterialHierarchically(materialFace)
        faceBack.position {
            this.scaleX = 2f
            this.scaleY = 1.8f
            this.y = -0.1f
            this.z = 0.9f
        }
        node.add(faceBack)

        val faceFace = faceBack.copy()
        faceFace.position {
            this.scaleX = 2f
            this.scaleY = 1.8f
            this.y = -0.1f
            this.z = 1.1f
        }
        node.add(faceFace)

        // Bridge
        this.bridge = this.createBridge()
        this.bridge.position {
            this.y = -1f
            this.z = 1.11f
        }
        node.add(this.bridge)

        // Floor
        val floor = this.plane.copy()
        floor.position {
            this.scaleX = 2.1f
            this.scaleY = 2.1f
            this.angleX = 90f
            this.y = -1.01f
        }
        node.add(floor)

        return node
    }

    /**
     * Launch animation to open the bridge.
     *
     * If bridge state is :
     * * [CastleBridgeState.CLOSED] : It opens immediately
     * * [CastleBridgeState.CLOSING] : It wait close and oen it just after
     * * [CastleBridgeState.OPENED] or [CastleBridgeState.OPENING] it does nothing
     *
     * See [bridgeState] to follow bridge state.
     *
     * @param scene Scene where animation is played
     * @param timeToOpenInMilliseconds Time it takes for open
     */
    fun open(scene: Scene3D, timeToOpenInMilliseconds: Int)
    {
        val state = this.bridgeStateMutable.value

        if (state == CastleBridgeState.OPENED || state == CastleBridgeState.OPENING)
        {
            return
        }

        if (state == CastleBridgeState.CLOSING)
        {
            this.bridgeState.doWhen(condition = { bridgeState -> bridgeState == CastleBridgeState.CLOSED },
                                    action = { this.open(scene, timeToOpenInMilliseconds) })

            return
        }

        this.bridgeStateMutable.value = CastleBridgeState.OPENING
        val animation = AnimationList()
        val animationBridge = AnimationNode3D(this.bridge)
        animationBridge.time(timeToOpenInMilliseconds,
                             Position3D(y = -1f,
                                        z = 1f,
                                        angleX = 90f),
                             AccelerationInterpolation(2f))
        animation.add(animationBridge)
        animation.add(animationTask(TaskType.SHORT_TASK) {
            this.bridgeStateMutable.value = CastleBridgeState.OPENED
        })
        scene.play(animation)
    }

    /**
     * Launch animation to close the bridge.
     *
     * If bridge state is :
     * * [CastleBridgeState.OPENED] : It closes immediately
     * * [CastleBridgeState.OPENING] : It wait it open and close it just after
     * * [CastleBridgeState.CLOSED] or [CastleBridgeState.CLOSING] it does nothing
     *
     * See [bridgeState] to follow bridge state.
     *
     * @param scene Scene where animation is played
     * @param timeToCloseInMilliseconds Timme it takes for close
     */
    fun close(scene: Scene3D, timeToCloseInMilliseconds: Int)
    {
        val state = this.bridgeStateMutable.value

        if (state == CastleBridgeState.CLOSED || state == CastleBridgeState.CLOSING)
        {
            return
        }

        if (state == CastleBridgeState.OPENING)
        {
            this.bridgeState.doWhen(condition = { bridgeState -> bridgeState == CastleBridgeState.OPENED },
                                    action = { this.close(scene, timeToCloseInMilliseconds) })

            return
        }

        this.bridgeStateMutable.value = CastleBridgeState.CLOSING
        val animation = AnimationList()
        val animationBridge = AnimationNode3D(this.bridge)
        animationBridge.time(timeToCloseInMilliseconds,
                             Position3D(y = -1f,
                                        z = 1.11f,
                                        angleX = 0f),
                             DecelerationInterpolation(2f))
        animation.add(animationBridge)
        animation.add(animationTask(TaskType.SHORT_TASK) {
            this.bridgeStateMutable.value = CastleBridgeState.CLOSED
        })
        scene.play(animation)
    }

    private fun creteTower(): Node3D
    {
        val node = Node3D()

        val path = Path()
        path.moveTo(0f, 0.9f)
        path.lineTo(-0.15f, 0.9f)
        path.lineTo(-0.15f, 1f)
        path.lineTo(-0.2f, 1f)
        path.lineTo(-0.2f, -1f)
        val cylinder = Revolution(path)
        cylinder.doubleFace = true
        cylinder.material = this.wallMaterial
        node.add(cylinder)

        val pathPart1 = Path()
        pathPart1.moveTo(-0.15f, 0f)
        pathPart1.lineTo(-0.15f, 0.1f)
        pathPart1.lineTo(-0.2f, 0.1f)
        pathPart1.lineTo(-0.2f, 0f)
        val cylinderPart1 = Revolution(pathPart1, 18f)
        cylinderPart1.doubleFace = true
        cylinderPart1.material = this.wallMaterial
        cylinderPart1.position.x = cylinder.position.x
        cylinderPart1.position.y = 1f
        cylinderPart1.position.z = cylinder.position.z
        node.add(cylinderPart1)

        for (angle in 0 until 360 step 18)
        {
            val separator = this.plane.copy()
            separator.position {
                this.scaleX = 0.05f
                this.scaleY = 0.2f

                this.angleY = 180f - angle

                this.x = cylinder.position.x - (0.175f * cos(degreeToRadian(angle.toFloat())))
                this.y = 1f
                this.z = cylinder.position.z - (0.175f * sin(degreeToRadian(angle.toFloat())))
            }
            node.add(separator)
        }

        val cylinderPart2 = cylinderPart1.copy()
        cylinderPart2.position.angleY = 36f
        cylinderPart2.position.x = cylinder.position.x
        cylinderPart2.position.y = cylinderPart1.position.y
        cylinderPart2.position.z = cylinder.position.z
        node.add(cylinderPart2)

        val cylinderPart3 = cylinderPart1.copy()
        cylinderPart3.position.angleY = 72f
        cylinderPart3.position.x = cylinder.position.x
        cylinderPart3.position.y = cylinderPart1.position.y
        cylinderPart3.position.z = cylinder.position.z
        node.add(cylinderPart3)

        val cylinderPart4 = cylinderPart1.copy()
        cylinderPart4.position.angleY = 108f
        cylinderPart4.position.x = cylinder.position.x
        cylinderPart4.position.y = cylinderPart1.position.y
        cylinderPart4.position.z = cylinder.position.z
        node.add(cylinderPart4)

        val cylinderPart5 = cylinderPart1.copy()
        cylinderPart5.position.angleY = 144f
        cylinderPart5.position.x = cylinder.position.x
        cylinderPart5.position.y = cylinderPart1.position.y
        cylinderPart5.position.z = cylinder.position.z
        node.add(cylinderPart5)

        val cylinderPart6 = cylinderPart1.copy()
        cylinderPart6.position.angleY = 180f
        cylinderPart6.position.x = cylinder.position.x
        cylinderPart6.position.y = cylinderPart1.position.y
        cylinderPart6.position.z = cylinder.position.z
        node.add(cylinderPart6)

        val cylinderPart7 = cylinderPart1.copy()
        cylinderPart7.position.angleY = 216f
        cylinderPart7.position.x = cylinder.position.x
        cylinderPart7.position.y = cylinderPart1.position.y
        cylinderPart7.position.z = cylinder.position.z
        node.add(cylinderPart7)

        val cylinderPart8 = cylinderPart1.copy()
        cylinderPart8.position.angleY = 252f
        cylinderPart8.position.x = cylinder.position.x
        cylinderPart8.position.y = cylinderPart1.position.y
        cylinderPart8.position.z = cylinder.position.z
        node.add(cylinderPart8)

        val cylinderPart9 = cylinderPart1.copy()
        cylinderPart9.position.angleY = 288f
        cylinderPart9.position.x = cylinder.position.x
        cylinderPart9.position.y = cylinderPart1.position.y
        cylinderPart9.position.z = cylinder.position.z
        node.add(cylinderPart9)

        val cylinderPart10 = cylinderPart1.copy()
        cylinderPart10.position.angleY = 324f
        cylinderPart10.position.x = cylinder.position.x
        cylinderPart10.position.y = cylinderPart1.position.y
        cylinderPart10.position.z = cylinder.position.z
        node.add(cylinderPart10)

        return node
    }

    private fun createFullWall(): Node3D
    {
        val node = Node3D()

        var plane = this.plane.copy()
        plane.position {
            this.scaleX = 2f
            this.scaleY = 1.8f
            this.y = -0.14f
            this.z = -0.1f
        }
        node.add(plane)

        plane = Castle.plane.copy()
        plane.position {
            this.scaleX = 2f
            this.scaleY = 1.8f
            this.y = -0.14f
            this.z = 0.1f
        }
        node.add(plane)

        plane = Castle.plane.copy()
        plane.position {
            this.scaleX = 2f
            this.scaleY = 0.3f
            this.y = 0.55f
            this.z = 0f
            this.angleX = 90f
        }
        node.add(plane)


        return node
    }

    private fun createBridge(): Node3D
    {
        val bridge = Node3D()

        val plank = this.plane.copy()
        plank.applyMaterialHierarchically(this.bridgeMaterial)
        plank.position {
            this.y = 0.55f
            this.scaleY = 1.1f
            this.scaleX = 0.8f
        }
        bridge.add(plank)

        return bridge
    }
}
