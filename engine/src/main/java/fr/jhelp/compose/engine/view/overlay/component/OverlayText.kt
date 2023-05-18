package fr.jhelp.compose.engine.view.overlay.component

import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import fr.jhelp.compose.engine.view.overlay.font.Alphabet
import fr.jhelp.compose.engine.view.overlay.font.DEFAULT_ALPHABET
import fr.jhelp.compose.images.Point
import kotlin.math.max

/**
 * Overlay with text
 */
class OverlayText(text: String = "", alphabet: Alphabet = DEFAULT_ALPHABET, factor: Int = 8) :
        OverlayComponent()
{
    /**
     * Text to draw
     */
    var text: String = text
        set(value)
        {
            if (field != value)
            {
                field = value
                this.refresh()
            }
        }

    /**
     * Alphabet used for draw teh text
     */
    var alphabet: Alphabet = alphabet
        set(value)
        {
            if (field != value)
            {
                field = value
                this.refresh()
            }
        }

    /**
     * Size multiplier factor
     */
    var factor: Int = max(1, factor)
        set(value)
        {
            val newValue = max(1, value)

            if (field != newValue)
            {
                field = newValue
                this.refresh()
            }
        }

    override fun draw(canvas: Canvas, paint: Paint)
    {
        var x = this.margin.left.toFloat()
        val maxX = this.width - this.margin.right
        var y = this.margin.top.toFloat()
        val maxY = this.height - this.margin.bottom
        val iterator = this.alphabet.convert(this.text, this.factor)

        while (iterator.hasNext())
        {
            val character = iterator.next()
            character.draw(x, y, canvas, paint)
            x += iterator.characterWidth

            if (character.endOfWorld && x + character.widthWorld >= maxX)
            {
                x = this.margin.left.toFloat()
                y += iterator.characterHeight

                if (y >= maxY)
                {
                    return
                }

                // Ignore space since we return line
                if (iterator.hasNext())
                {
                    iterator.next()
                }
            }
        }
    }

    override fun layout()
    {
    }

    override fun preferredSize(parentWidth: Int, parentHeight: Int): Point
    {
        var x = this.margin.left
        var maxX = 0

        val iterator = this.alphabet.convert(this.text, this.factor)
        var y = iterator.characterHeight + this.margin.top

        while (iterator.hasNext())
        {
            val character = iterator.next()
            x += iterator.characterWidth
            maxX = max(x, maxX)

            if (character.endOfWorld && x + character.widthWorld >= parentWidth - this.margin.width)
            {
                x = 0
                y += iterator.characterHeight
            }
        }

        return Point(maxX, y)
    }
}
