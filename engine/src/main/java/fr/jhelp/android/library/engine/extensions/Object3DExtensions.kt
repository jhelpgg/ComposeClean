package fr.jhelp.android.library.engine.extensions

import fr.jhelp.android.library.engine.dsl.MaterialReference
import fr.jhelp.android.library.engine.scene.Object3D

/**
 * Apply material referred by a material reference to this object 3D
 */
fun Object3D.material(materialReference: MaterialReference)
{
    this.material = materialReference.material
}
