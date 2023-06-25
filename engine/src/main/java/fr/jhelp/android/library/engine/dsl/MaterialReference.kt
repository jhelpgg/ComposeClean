package fr.jhelp.android.library.engine.dsl

import fr.jhelp.android.library.engine.scene.Color3D
import fr.jhelp.android.library.engine.scene.Material

/**
 * Create a reference on material
 */
class MaterialReference internal constructor(internal val material: Material = Material()) {
    /** Diffuse color */
    val diffuse : Color3D get() = this.material.diffuse
    /** Transparency */
    val alpha : Float get() = this.material.alpha
}
