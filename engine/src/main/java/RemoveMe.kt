import fr.jhelp.android.library.engine.scene.LIGHT_BLUE
import fr.jhelp.android.library.engine.scene.LIGHT_GREY
import fr.jhelp.android.library.engine.scene.Scene3D
import fr.jhelp.android.library.engine.scene.YELLOW
import fr.jhelp.android.library.engine.scene.geometry.Sphere

fun sceneReady(scene3D: Scene3D)
{
    scene3D.root.position.z = -5f

    val sun = Sphere()
    sun.material.diffuse = YELLOW
    scene3D.root.add(sun)

    val earth = Sphere()
    earth.position.scale(0.4f)
    earth.position.y = 2f
    earth.material.diffuse = LIGHT_BLUE
    sun.add(earth)

    val moon = Sphere()
    moon.position.scale(0.4f)
    moon.position.y = 2f
    moon.material.diffuse = LIGHT_GREY
    earth.add(moon)
}
