package fr.jhelp.compose.engine.view.overlay.font

import android.graphics.Canvas
import android.graphics.Paint
import fr.jhelp.compose.collection.IntList

/**
 * Alphabet character to draw
 * @property endOfWorld Indicates if the characters is the end of a word (so can be cut)
 * @property widthWorld Width in pixels of current word, if endOfWord is true,the width is width of next world
 * @property isSpace Indicates if it is a space
 */
class AlphabetCharacter internal constructor(val endOfWorld: Boolean,
                                             val widthWorld: Int,
                                             val isSpace: Boolean,
                                             private val emphasis: Boolean,
                                             private val charactersOffset: IntList,
                                             private val factor: Float,
                                             private val alphabet: Alphabet)
{
    /**
     * Draw the character
     */
    fun draw(x: Float, y: Float, canvas: Canvas, paint: Paint)
    {
        for (index in 0 until this.charactersOffset.size)
        {
            this.alphabet.draw(this.emphasis, this.charactersOffset[index], this.factor,
                               x, y, canvas, paint)
        }
    }
}