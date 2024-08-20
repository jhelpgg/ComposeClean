package fr.jhelp.android.library.engine.view.overlay

import android.graphics.Canvas
import android.graphics.Paint
import fr.jhelp.android.library.engine.view.overlay.component.OverlayComponent
import fr.jhelp.android.library.engine.view.overlay.component.OverlayText
import fr.jhelp.android.library.tasks.extensions.delay
import fr.jhelp.android.library.tasks.extensions.parallel
import fr.jhelp.android.library.tasks.future.FutureResult

class OverlayScreenAnimation(private var confirmSkip: Boolean,
                             private val skipWhenFinished: Boolean,
                             animationFuture: FutureResult<Unit>,
                             private val onSkip: () -> Unit) : OverlayScreen {
    companion object {
        private const val SKIP_MESSAGE_TIMEOUT = 2048L
    }

    private var width = 1f
    private var height = 1f
    private var refresh: () -> Unit = {}
    private val mainComponent: OverlayComponent = OverlayText("Click again to skip")

    init {
        this.mainComponent.visible = false

        animationFuture.and {
            this.confirmSkip = false

            if (this.skipWhenFinished) {
                this.onSkip()
            }
        }
    }

    override fun registerRefresh(refresh: () -> Unit) {
        this.refresh = refresh
        this.mainComponent.refresh = refresh
    }

    override fun size(width: Int, height: Int) {
        this.width = width.toFloat()
        this.height = height.toFloat()
        val size = this.mainComponent.preferredSize(width, height)
        this.mainComponent.x = this.width - size.x
        this.mainComponent.y = 0f
        this.mainComponent.width = size.x.toFloat()
        this.mainComponent.height = size.y.toFloat()
        this.mainComponent.layout()
    }

    override fun draw(canvas: Canvas, paint: Paint) {
        if (this.mainComponent.visible) {
            this.mainComponent.draw(canvas, paint)
        }
    }

    override fun touchDown(x: Float, y: Float) = Unit

    override fun touchUp(x: Float, y: Float) = Unit

    override fun touchMove(xPrevious: Float, yPrevious: Float, x: Float, y: Float) = Unit

    override fun touchClick(x: Float, y: Float) {
        if (this.confirmSkip.not() || this.mainComponent.visible) {
            this.onSkip.parallel()
            this.mainComponent.visible = false
            return
        }

        this.mainComponent.visible = true

        ({ this.mainComponent.visible = false }).delay(OverlayScreenAnimation.SKIP_MESSAGE_TIMEOUT)
    }
}