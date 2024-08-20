package fr.jhelp.android.library.showcase.game.models

import fr.jhelp.android.library.engine.scene.Color3D
import fr.jhelp.android.library.engine.scene.Material
import fr.jhelp.android.library.engine.scene.Node3D
import fr.jhelp.android.library.engine.scene.geometry.Revolution
import fr.jhelp.android.library.images.path.Path
import fr.jhelp.android.library.math.COLOR_BROWN_0300
import fr.jhelp.android.library.math.COLOR_GREEN_0700

class Tree : Model3D() {
    private val leafMaterial : Material by lazy {
        val material = Material()
        material.diffuse = Color3D(COLOR_GREEN_0700)
        material
    }

    private val trunkMaterial : Material by lazy {
        val material = Material()
        material.diffuse = Color3D(COLOR_BROWN_0300)
        material
    }

    override fun create(): Node3D {
        val mainNode = Node3D()
        mainNode .add(this.leafs())
        mainNode .add(this.trunk())
        return mainNode
    }

    private fun leafs(): Node3D {
        val path = Path()
        path.moveTo(0f, 0.5f)
        path.lineTo(0.2f, 0f)
        path.lineTo(0.25f, 0f)
        path.lineTo(0.4f, -0.5f)
        path.lineTo(0.45f, -0.5f)
        path.lineTo(0.6f, -1f)
        path.lineTo(0f, -1f)
        val revolution = Revolution(path)
        revolution.position.y = 1f
        revolution.material = this.leafMaterial
        return revolution
    }

    private fun trunk() : Node3D
    {
        val path = Path()
        path.moveTo(0.1f, 1.25f)
        path.lineTo(0.1f, -1.25f)
        val revolution = Revolution(path)
        revolution.material = this.trunkMaterial
        return revolution
    }
}