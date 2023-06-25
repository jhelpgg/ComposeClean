package fr.jhelp.android.library.engine.resources.image

import android.graphics.Bitmap

/**
 * Image comes from a source
 */
sealed class ImageSource<IS : ImageSource<IS>>
{
    /** Image carry by the source */
    val image: Bitmap by lazy { this.createBitmap() }

    override fun equals(other: Any?): Boolean
    {
        if (other === this)
        {
            return true
        }

        if (other == null || other.javaClass != this.javaClass)
        {
            return false
        }

        return this.equalsSource(other as IS)
    }

    override fun hashCode(): Int =
        this.hash() * 31 + this.javaClass.name.hashCode()

    /**
     * Create the image form the source
     */
    protected abstract fun createBitmap(): Bitmap

    /**
     * Indicates if refer same resource
     */
    protected abstract fun equalsSource(source: IS): Boolean

    /**
     * Compute specific hash code part
     */
    protected abstract fun hash(): Int
}