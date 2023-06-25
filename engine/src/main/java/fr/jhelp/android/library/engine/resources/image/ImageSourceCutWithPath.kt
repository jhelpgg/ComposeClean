package fr.jhelp.android.library.engine.resources.image

import android.graphics.Bitmap
import fr.jhelp.android.library.images.bitmap
import fr.jhelp.android.library.images.createBitmap
import fr.jhelp.android.library.images.path.Path
import fr.jhelp.android.library.math.extensions.bounds
import java.util.Objects

/**
 * Image source that keep only part inside a given path
 */
class ImageSourceCutWithPath(private val imageSource: ImageSource<*>,
                             private val path: Path, pathPrecision: Int = 5) :
        ImageSource<ImageSourceCutWithPath>()
{
    private val pathPrecision: Int = pathPrecision.bounds(2, 10)

    override fun createBitmap(): Bitmap
    {
        val image = this.imageSource.image
        val segments = this.path.path(this.pathPrecision, 0f, 1f)

        if (segments.isEmpty())
        {
            return image
        }

        return createBitmap(image.width,
                            image.height) { bitmap, canvas, _ ->
            val width = bitmap.width.toFloat()
            val height = bitmap.height.toFloat()
            var minX = Float.POSITIVE_INFINITY
            var maxX = Float.NEGATIVE_INFINITY
            var minY = Float.POSITIVE_INFINITY
            var maxY = Float.NEGATIVE_INFINITY

            for (segment in segments)
            {
                minX = minOf(minX, segment.startX, segment.endX)
                maxX = maxOf(maxX, segment.startX, segment.endX)
                minY = minOf(minY, segment.startY, segment.endY)
                maxY = maxOf(maxY, segment.startY, segment.endY)
            }

            val factorX = width / (if (minX == maxX) 1f else maxX - minX)
            val factorY = height / (if (minY == maxY) 1f else maxY - minY)

            val androidPath = android.graphics.Path()
            androidPath.moveTo((segments[0].startX - minX) * factorX,
                               height - ((segments[0].startY - minY) * factorY))

            for (segment in segments)
            {
                androidPath.lineTo((segment.endX - minX) * factorX,
                                   height - ((segment.endY - minY) * factorY))
            }

            androidPath.close()
            canvas.clipPath(androidPath)
            canvas.bitmap(image, 0f, 0f)
        }
    }

    override fun equalsSource(source: ImageSourceCutWithPath): Boolean =
        this.imageSource == source.imageSource &&
                this.path == source.path && this.pathPrecision == source.pathPrecision

    override fun hash(): Int = Objects.hash(this.imageSource, this.path, this.pathPrecision)
}