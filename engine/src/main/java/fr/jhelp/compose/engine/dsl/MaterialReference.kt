package fr.jhelp.compose.engine.dsl

import fr.jhelp.compose.engine.scene.Material

/**
 * Create a reference on material
 */
class MaterialReference internal constructor(internal val material: Material = Material())
