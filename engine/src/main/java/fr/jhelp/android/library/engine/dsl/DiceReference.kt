package fr.jhelp.android.library.engine.dsl

import fr.jhelp.android.library.engine.scene.Scene3D
import fr.jhelp.android.library.engine.scene.geometry.dice.Dice

/**
 * Reference to a dice object
 */
class DiceReference internal constructor(internal val scene: Scene3D,
                                         internal val dice: Dice = Dice())
