package fr.jhelp.compose.engine.extensions

import fr.jhelp.compose.engine.dsl.MaterialReference
import fr.jhelp.compose.engine.scene.Clone3D

/**
 * Apply material reference to a clone
 */
fun Clone3D.material(materialReference: MaterialReference)
{
    this.material = materialReference.material
}
