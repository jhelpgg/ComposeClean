package fr.jhelp.android.library.engine.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import fr.jhelp.android.library.engine.R
import fr.jhelp.android.library.engine.view.overlay.GameOverlay
import fr.jhelp.android.library.engine.view.overlay.OverlayScreen
import fr.jhelp.android.library.engine.view.touch.View3DTouchNothing
import fr.jhelp.android.library.tasks.extensions.parallel

/**
 * Generic activity for 3D games.
 *
 * Recommended to use with theme `Game3D` locked in landscape. Something like :
 *
 * ```xml
 *  <activity
 *     android:name=".YourGameActivity"
 *     android:screenOrientation="landscape"
 *     android:theme="@style/Theme.Game3D"
 * ```
 */
abstract class GameActivity : ComponentActivity()
{
    private lateinit var overlay: GameOverlay

    /**
     * Called when activity is created
     */
    @MainThread
    final override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.activity_3d_game)
        this.overlay = this.findViewById(R.id.overlay)
        val view3D: View3D = this.findViewById(R.id.view3D)
        view3D.view3DTouchAction = View3DTouchNothing
        this.createGameActivity()
        this::startGame.parallel(view3D)
    }

    /**
     * Change the overlay screen
     */
    fun screen(screen: OverlayScreen)
    {
        this.overlay.screen(screen)
    }

    /**
     * Called when game activity created
     */
    @MainThread
    protected open fun createGameActivity(): Unit = Unit

    /**
     * Called when game started
     */
    abstract fun startGame(view: View3D)
}
