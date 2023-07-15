import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.scene.geometry.robot.Robot
import fr.jhelp.android.library.engine.view.View3D

fun sceneReady(view3D: View3D)
{
    val robot = Robot()

    view3D.tree {
        this.root {
            this.node { this.add(robot.mainNode) }
        }
    }

}
