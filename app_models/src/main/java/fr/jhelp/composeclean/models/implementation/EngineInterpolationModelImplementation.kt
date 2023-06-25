package fr.jhelp.composeclean.models.implementation

import android.graphics.Paint
import android.graphics.Rect
import fr.jhelp.compose.animations.Animation
import fr.jhelp.compose.animations.AnimationList
import fr.jhelp.compose.animations.AnimationLoop
import fr.jhelp.compose.animations.AnimationTask
import fr.jhelp.compose.animations.interpolation.AccelerationInterpolation
import fr.jhelp.compose.animations.interpolation.AnticipateInterpolation
import fr.jhelp.compose.animations.interpolation.AnticipateOvershootInterpolation
import fr.jhelp.compose.animations.interpolation.BounceInterpolation
import fr.jhelp.compose.animations.interpolation.BouncingInterpolation
import fr.jhelp.compose.animations.interpolation.CosinusInterpolation
import fr.jhelp.compose.animations.interpolation.CubicInterpolation
import fr.jhelp.compose.animations.interpolation.DecelerationInterpolation
import fr.jhelp.compose.animations.interpolation.ExponentialInterpolation
import fr.jhelp.compose.animations.interpolation.HesitateInterpolation
import fr.jhelp.compose.animations.interpolation.Interpolation
import fr.jhelp.compose.animations.interpolation.LinearInterpolation
import fr.jhelp.compose.animations.interpolation.LogarithmInterpolation
import fr.jhelp.compose.animations.interpolation.OvershootInterpolation
import fr.jhelp.compose.animations.interpolation.QuadraticInterpolation
import fr.jhelp.compose.animations.interpolation.RandomInterpolation
import fr.jhelp.compose.animations.interpolation.SinusInterpolation
import fr.jhelp.compose.animations.interpolation.SquareInterpolation
import fr.jhelp.compose.animations.interpolation.SquareRootInterpolation
import fr.jhelp.compose.engine.animation.keyFrame.AnimationNode3D
import fr.jhelp.compose.engine.extensions.position
import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.compose.engine.resources.draw
import fr.jhelp.compose.engine.resources.texture
import fr.jhelp.compose.engine.scene.LIGHT_GREY
import fr.jhelp.compose.engine.scene.Position3D
import fr.jhelp.compose.engine.scene.Scene3D
import fr.jhelp.compose.engine.scene.geometry.Box
import fr.jhelp.android.library.images.clear
import fr.jhelp.android.library.math.COLOR_BLACK
import fr.jhelp.android.library.math.COLOR_BLUE_0400
import fr.jhelp.android.library.math.COLOR_GREY_0400
import fr.jhelp.android.library.math.COLOR_WHITE
import fr.jhelp.composeclean.models.shared.EngineInterpolationModel
import fr.jhelp.android.library.tasks.TaskType

internal class EngineInterpolationModelImplementation : EngineInterpolationModel
{
    companion object
    {
        private val interpolations =
            arrayOf<Interpolation>(
                LinearInterpolation,
                AccelerationInterpolation(2f),
                AnticipateInterpolation(2f),
                AnticipateOvershootInterpolation(2f),
                BounceInterpolation,
                BouncingInterpolation(5),
                CosinusInterpolation,
                CubicInterpolation(0.1f, 1.1f),
                DecelerationInterpolation(2f),
                ExponentialInterpolation,
                HesitateInterpolation,
                LogarithmInterpolation,
                OvershootInterpolation(2f),
                QuadraticInterpolation(0.1f),
                RandomInterpolation,
                SinusInterpolation,
                SquareInterpolation,
                SquareRootInterpolation
                                  )

        private val cubeFirstPosition = Position3D(x = -3.2916336f, y = 7.6980405f, z = -7.4116883f,
                                                   angleX = 0.0f, angleY = 0.0f, angleZ = 0.0f,
                                                   scaleX = 1.0f, scaleY = 1.0f, scaleZ = 1.0f)
        private val cubeSecondPosition = Position3D(x = 3.1695871f, y = 7.8112297f, z = -7.4116883f,
                                                    angleX = 0.0f, angleY = 0.0f, angleZ = 0.0f,
                                                    scaleX = 1.0f, scaleY = 1.0f, scaleZ = 1.0f)
        private val cubeThirdPosition = Position3D(x = 1.9954292f, y = -4.984398f, z = -4.7834296f,
                                                   angleX = 0.0f, angleY = 0.0f, angleZ = 0.0f,
                                                   scaleX = 1.0f, scaleY = 1.0f, scaleZ = 1.0f)
        private val cubeFourthPosition =
            Position3D(x = -2.1095712f, y = -5.1263995f, z = -4.7834296f,
                       angleX = 0.0f, angleY = 0.0f, angleZ = 0.0f,
                       scaleX = 1.0f, scaleY = 1.0f, scaleZ = 1.0f)

        private const val INTERPOLATION_SIZE = "Interpolation".length
    }

    private lateinit var scene: Scene3D
    private val cube = Box()
    private val animation: Animation
    private val background = texture(512, 512)

    init
    {
        this.cube.material.diffuse = LIGHT_GREY
        val animationList = AnimationList()

        for (interpolation in EngineInterpolationModelImplementation.interpolations)
        {
            this.animationInterpolation(animationList, interpolation)
        }

        this.animation = AnimationLoop(animationList)
    }

    override fun scene(scene: Scene3D, texture: Int)
    {
        this.scene = scene
        this.cube.material.texture = ResourcesAccess.obtainTexture(texture)
        scene.textureBackground = this.background
        scene.root.position { this.z = -2f }
        scene.root.removeAllChildren()
        scene.root.add(this.cube)
        scene.play(this.animation)
    }

    override fun stopAnimation()
    {
        this.scene.stop(this.animation)
        this.scene.root.remove(this.cube)
    }

    private fun animationInterpolation(animationList: AnimationList, interpolation: Interpolation)
    {
        animationList.add(AnimationTask(TaskType.SHORT_TASK, interpolation, this::draw))
        val animation = AnimationNode3D(this.cube)

        animation.frame(0, EngineInterpolationModelImplementation.cubeFirstPosition, interpolation)
        animation.frame(50,
                        EngineInterpolationModelImplementation.cubeSecondPosition,
                        interpolation)
        animation.frame(100,
                        EngineInterpolationModelImplementation.cubeThirdPosition,
                        interpolation)
        animation.frame(150,
                        EngineInterpolationModelImplementation.cubeFourthPosition,
                        interpolation)
        animation.frame(200,
                        EngineInterpolationModelImplementation.cubeFirstPosition,
                        interpolation)

        animationList.add(animation)
    }

    private fun draw(interpolation: Interpolation)
    {
        this.background.draw { bitmap, canvas, paint ->
            bitmap.clear(COLOR_WHITE)
            paint.style = Paint.Style.FILL
            paint.textSize = 32f
            paint.textAlign = Paint.Align.CENTER
            var text = interpolation::class.java.simpleName
            text = text.substring(0,
                                  text.length - EngineInterpolationModelImplementation.INTERPOLATION_SIZE)
            val bounds = Rect()
            paint.getTextBounds(text, 0, text.length, bounds)
            paint.color = COLOR_BLACK
            canvas.drawText(text, 256f, 16f - bounds.top, paint)

            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 1f
            paint.color = COLOR_BLACK
            canvas.drawLine(0f, 384f, 512f, 384f, paint)
            paint.color = COLOR_GREY_0400
            canvas.drawLine(0f, 128f, 512f, 128f, paint)

            paint.strokeWidth = 3f
            paint.color = COLOR_BLUE_0400
            var oldX = 0f
            var oldY = 384f

            for (xx in 0..512)
            {
                val x = xx.toFloat()
                val y = 384f - interpolation(x / 512f) * 256f
                canvas.drawLine(oldX, oldY, x, y, paint)
                oldX = x
                oldY = y
            }
        }
    }
}
