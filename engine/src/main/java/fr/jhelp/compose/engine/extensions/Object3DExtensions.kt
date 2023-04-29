package fr.jhelp.compose.engine.extensions

import fr.jhelp.compose.engine.dsl.MaterialReference
import fr.jhelp.compose.engine.scene.Object3D

/**
 * Apply material referred by a material reference to this object 3D
 */
fun Object3D.material(materialReference: MaterialReference)
{
    this.material = materialReference.material
}
