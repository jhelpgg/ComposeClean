import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D
import fr.jhelp.android.library.math.AngleFloat
import fr.jhelp.android.library.math.AngleUnit

fun sceneReady(view3D: View3D)
{
    view3D.tree {
        this.root {
            this.revolution(
                rotationPrecision = 5,
                path = {
                    this.moveTo(0.2f, 1f)
                    this.lineTo(0.22f, 0f)
                    this.ellipticArcTo(radiusX = 0.5f, radiusY = 0.5f,
                                       rotationAxisX = AngleFloat(90f, AngleUnit.DEGREE),
                                       largeArc = true, sweep = false,
                                       endX = 0f, endY = -1f)
                }) {
                // ..
            }
        }
    }
}
