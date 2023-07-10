import fr.jhelp.android.library.engine.extensions.position
import fr.jhelp.android.library.engine.scene.geometry.Box

fun t()
{
    val object3D = Box()

    // Standard scale all axis with same value
    object3D.position.scale(1.23f)
    // Standard scale different on axis
    object3D.position.scale(1.23f, 2.5f, .3f)

    // DSL
    object3D.position {
        // All axis with same value
        this.scale(1.23f)
        // Scale different on axis
        this.scale(1.23f, 2.5f, .3f)
    }
}