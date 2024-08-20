package fr.jhelp.android.library.showcase.game

import android.graphics.Paint
import fr.jhelp.android.library.animations.Animation
import fr.jhelp.android.library.animations.AnimationFunction
import fr.jhelp.android.library.animations.AnimationList
import fr.jhelp.android.library.animations.animationTask
import fr.jhelp.android.library.engine.animation.keyFrame.AnimationNode3D
import fr.jhelp.android.library.engine.extensions.position
import fr.jhelp.android.library.engine.resources.draw
import fr.jhelp.android.library.engine.resources.texture
import fr.jhelp.android.library.engine.scene.Node3D
import fr.jhelp.android.library.engine.scene.Scene3D
import fr.jhelp.android.library.engine.scene.geometry.Plane
import fr.jhelp.android.library.engine.view.overlay.OverlayScreen
import fr.jhelp.android.library.engine.view.overlay.OverlayScreenAnimation
import fr.jhelp.android.library.math.COLOR_GREEN_0300
import fr.jhelp.android.library.math.COLOR_GREY_0300
import fr.jhelp.android.library.showcase.game.event.NextScreen
import fr.jhelp.android.library.showcase.game.models.Castle
import fr.jhelp.android.library.showcase.game.models.Tree
import fr.jhelp.android.library.tasks.TaskType
import fr.jhelp.android.library.tasks.future.Promise

class GameScreenIntroductionScene(scene: Scene3D) : GameScreen(scene) {
    private val promiseAnimationFinished = Promise<Unit>()

    override val overlay: OverlayScreen =
        OverlayScreenAnimation(
            confirmSkip = true,
            skipWhenFinished = true,
            animationFuture = this.promiseAnimationFinished.future,
            onSkip = this::onSkip
                              )

    private val castle = Castle()
    private val tree = Tree().node
    private val road: Node3D = this.road()
    private val animation: Animation = this.animation()

    override fun attached() {
        this.scene.root.position.z = -3f
        this.scene.root.add(this.castle.node)
        var z = -5f

        for (roadPart in 0 until 20) {
            val clone = this.road.copy()
            clone.position {
                this.scaleX = 15f
                this.angleX = 90f
                this.y = -1.01f
                this.z = z
            }

            this.scene.root.add(clone)
            z += 1f
        }

        this.scene.play(this.animation)
    }

    override fun detached() {
    }

    private fun onSkip() {
        this.publish(NextScreen)
    }

    private fun road(): Node3D {
        val plane = Plane()
        plane.material.texture = texture(512, 512).draw { bitmap, canvas, paint ->
            paint.color = COLOR_GREEN_0300
            paint.style = Paint.Style.FILL
            canvas.drawRect(0f, 0f, 512f, 512f, paint)
            paint.color = COLOR_GREY_0300
            canvas.drawRect(240f, 0f, 270f, 512f, paint)
        }

        return plane
    }

    private fun animation(): Animation {
        val animationList = AnimationList()
        val animation = AnimationNode3D(this.scene.root)
        animation.frame(0, this.scene.root.position.copy(z = -15f))
        animation.frame(100, this.scene.root.position.copy(z = -3f))
        animationList.add(animation)
        animationList.add(animationTask(TaskType.SHORT_TASK){this.promiseAnimationFinished.result(Unit)})
        return animationList
    }
}