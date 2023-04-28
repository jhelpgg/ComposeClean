package fr.jhelp.compose.engine.loaders

import fr.jhelp.compose.engine.scene.Node3D
import java.io.IOException
import java.io.InputStream

interface NodeLoader
{
    @Throws(IOException::class)
    fun load(name: String, inputStream: InputStream, seal: Boolean = true): Node3D
}