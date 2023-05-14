package fr.jhelp.compose.engine.dsl

import fr.jhelp.compose.engine.scene.Scene3D
import fr.jhelp.compose.engine.scene.geometry.dice.Dice

class DiceReference internal constructor(internal val scene: Scene3D,
                                         internal val dice: Dice = Dice())
