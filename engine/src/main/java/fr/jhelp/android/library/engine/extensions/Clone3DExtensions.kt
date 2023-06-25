package fr.jhelp.android.library.engine.extensions

import fr.jhelp.android.library.engine.dsl.MaterialReference
import fr.jhelp.android.library.engine.scene.Clone3D

/**
 * Apply material reference to a clone
 */
fun Clone3D.material(materialReference: MaterialReference)
{
    this.material = materialReference.material
}
