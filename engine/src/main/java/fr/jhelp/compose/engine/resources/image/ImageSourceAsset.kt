package fr.jhelp.compose.engine.resources.image

import android.graphics.Bitmap
import fr.jhelp.compose.engine.resources.ResourcesAccess

/**
 * Image from an asset
 */
class ImageSourceAsset(private val assetPath: String) : ImageSource<ImageSourceAsset>()
{
    override fun createBitmap(): Bitmap = ResourcesAccess.obtainBitmap(this.assetPath)

    override fun equalsSource(source: ImageSourceAsset): Boolean =
        this.assetPath == source.assetPath

    override fun hash(): Int = this.assetPath.hashCode()
}
