package fr.jhelp.compose.engine.resources

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.DrawableRes
import fr.jhelp.compose.engine.annotations.TextureSize
import fr.jhelp.compose.engine.resources.image.ImageSource
import fr.jhelp.compose.engine.scene.Texture
import fr.jhelp.compose.math.log2
import java.io.InputStream
import kotlin.math.max
import kotlin.math.min

/**
 * Create a default texture
 */
fun defaultTexture(): Texture =
    texture(ResourcesAccess.defaultBitmap())

/**
 * Create texture from resources
 */
fun texture(resources: Resources, @DrawableRes resourceID: Int, sealed: Boolean = true): Texture
{
    val options = BitmapFactory.Options()
    options.inScaled = false
    return BitmapFactory.decodeResource(resources, resourceID, options)
        ?.let { bitmap -> texture(bitmap, sealed) } ?: defaultTexture()
}

/**
 * Create empty texture with given dimension
 */
fun texture(@TextureSize width: Int, @TextureSize height: Int): Texture
{
    val goodWidth = 1 shl min(9, log2(max(1, width)))
    val goodHeight = 1 shl min(9, log2(max(1, height)))
    return Texture(Bitmap.createBitmap(goodWidth, goodHeight, Bitmap.Config.ARGB_8888), false)
}

/**
 * Create texture from a stream
 */
fun texture(inputStreamProvider: () -> InputStream, sealed: Boolean = true): Texture
{
    val options = BitmapFactory.Options()
    options.inScaled = false
    options.inJustDecodeBounds = true
    var inputStream = inputStreamProvider()
    BitmapFactory.decodeStream(inputStream, null, options)
    inputStream.close()
    val width: Int = options.outWidth
    val log2Width: Int = log2(width)
    val height: Int = options.outHeight
    val log2Height: Int = log2(height)
    val log2 = max(log2Width, log2Height)
    options.inJustDecodeBounds = false
    options.inSampleSize = 1

    if (log2 > 9)
    {
        options.inSampleSize = 1 shl (log2 - 9)
    }

    inputStream = inputStreamProvider()
    val texture = BitmapFactory.decodeStream(inputStream, null, options)
        ?.let { bitmap -> texture(bitmap, sealed) }
        ?: defaultTexture()
    inputStream.close()
    return texture
}

/**
 * Create texture from bitmap
 */
fun texture(bitmap: Bitmap, sealed: Boolean = true): Texture
{
    val width = bitmap.width
    val height = bitmap.height
    val goodWidth = 1 shl min(9, log2(width))
    val goodHeight = 1 shl min(9, log2(height))

    return if (width != goodWidth || height != goodHeight)
    {
        val resized = Bitmap.createScaledBitmap(bitmap, goodWidth, goodHeight, false)
        bitmap.recycle()
        Texture(resized, sealed)
    }
    else
    {
        Texture(bitmap, sealed)
    }
}

/**
 * Modify a texture, if not sealed.
 *
 * Automatically refreshed
 *
 * @param draw Described what drawing if texture not null and not sealed
 */
fun Texture?.draw(draw: (Bitmap, Canvas, Paint) -> Unit): Texture?
{
    val bitmap = this?.bitmap()
    val canvas = this?.canvas()
    val paint = this?.paint()

    if (bitmap != null && canvas != null && paint != null)
    {
        draw(bitmap, canvas, paint)
        this?.refresh()
    }

    return this
}

/**
 * Create texture from image source
 */
fun texture(imageSource: ImageSource<*>, sealed: Boolean = true): Texture
{
    val bitmap = imageSource.image
    val width = bitmap.width
    val height = bitmap.height
    val goodWidth = 1 shl min(9, log2(width))
    val goodHeight = 1 shl min(9, log2(height))

    return if (width != goodWidth || height != goodHeight)
    {
        val resized = Bitmap.createScaledBitmap(bitmap, goodWidth, goodHeight, false)
        Texture(resized, sealed)
    }
    else
    {
        Texture(bitmap, sealed)
    }
}

