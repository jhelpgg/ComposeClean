package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import fr.jhelp.compose.engine.R
import fr.jhelp.compose.engine.dsl.material
import fr.jhelp.compose.engine.dsl.materialReference
import fr.jhelp.compose.engine.dsl.texture
import fr.jhelp.compose.engine.dsl.textureReference
import fr.jhelp.compose.engine.extensions.position
import fr.jhelp.compose.engine.loaders.objLoader.ObjLoader
import fr.jhelp.compose.engine.scene.WHITE
import fr.jhelp.compose.ui.engine.View3DComposable

/**
 * Show the engine 3d load a obj file. Here a teddy bear
 */
class EngineLoadObjComposable
{
    /**
     * Show the engine 3d load a obj file. Here a teddy bear
     */
    @Composable
    fun Show()
    {
        val view3D = View3DComposable()
        val textureBear = textureReference()
        texture(textureBear, R.drawable.toybear_default_color)
        val materialBear = materialReference()
        material(materialBear) {
            this.diffuse = WHITE
            this.textureReference = textureBear
        }
        view3D.Draw(modifier = Modifier.fillMaxSize()) {
            this.scenePosition { this.z = -2.5f }
            this.root {
                this.load(name = "Teddy bear",
                          rawID = R.raw.toybear,
                          loader = ObjLoader,
                          material = materialBear) { this.position { this.setScale(0.01f) } }
            }
        }
    }
}