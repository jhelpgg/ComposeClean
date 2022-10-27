package fr.jhelp.compose.engine.extensions

import fr.jhelp.compose.engine.scene.Position3D

operator fun Number.times(position3D: Position3D) : Position3D = position3D * this