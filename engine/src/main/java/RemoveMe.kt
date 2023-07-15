import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D
import fr.jhelp.android.library.math.PI_FLOAT
import fr.jhelp.android.library.math.formal.X
import fr.jhelp.android.library.math.formal.Y
import fr.jhelp.android.library.math.formal.cos
import fr.jhelp.android.library.math.formal.sin
import fr.jhelp.android.library.math.formal.times

fun sceneReady(view3D: View3D)
{
    view3D.tree {
        this.root {
            this.field(
                functionZ = cos(X) * sin(Y),
                xStart = -PI_FLOAT, xEnd = PI_FLOAT, numberPartX = 10,
                yStart = -PI_FLOAT, yEnd = PI_FLOAT, numberPartY = 10) {
                // ...
            }
        }
    }

}
