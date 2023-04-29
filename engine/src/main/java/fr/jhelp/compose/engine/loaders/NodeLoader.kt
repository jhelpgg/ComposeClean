package fr.jhelp.compose.engine.loaders

import fr.jhelp.compose.engine.scene.Node3D
import java.io.IOException
import java.io.InputStream

/**
 * Describe how to load a node hierarchy from a file format
 */
interface NodeLoader
{
    /**
     * Load nde hierarchy from stream on suppose it respects the attended file format
     * @param name Name to give to the main tre of the hierarchy
     * @param inputStream Stream to parse
     * @param seal Indicates if have to seal 3D objects found in the hierarchy
     * @throws IOException On read stream issue or stream not respects the attended file format
     */
    @Throws(IOException::class)
    fun load(name: String, inputStream: InputStream, seal: Boolean = true): Node3D
}