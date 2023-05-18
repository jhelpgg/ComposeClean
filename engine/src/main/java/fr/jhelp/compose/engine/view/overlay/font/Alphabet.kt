package fr.jhelp.compose.engine.view.overlay.font

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import androidx.annotation.DrawableRes
import fr.jhelp.compose.collection.IntList
import fr.jhelp.compose.collection.queue.Queue
import fr.jhelp.compose.engine.resources.ResourcesAccess
import java.text.Normalizer
import kotlin.math.max

/**
 * Alphabet description
 *
 * @property characterWidth Character base width
 * @property characterHeight Character base height
 */
class Alphabet(val characterWidth: Int,
               val characterHeight: Int,
               @DrawableRes private val normalCharacters: Int,
               @DrawableRes private val emphasisCharacters: Int,
               private val charactersOrder: CharArray,
               private val numberCharactersPerLine: Int,
               private val tildeDeltaY: Int = 0)
{
    companion object
    {
        @JvmStatic
        private fun worldWidth(worldIndex: Int, offsets: List<IntList>): Int
        {
            if (offsets[worldIndex].empty)
            {
                return 1
            }

            var startIndex = worldIndex

            while (startIndex > 0 && offsets[startIndex - 1].notEmpty)
            {
                startIndex--
            }

            var endIndex = worldIndex

            while (endIndex + 1 < offsets.size && offsets[endIndex + 1].notEmpty)
            {
                endIndex++
            }

            return endIndex - startIndex + 1
        }
    }

    private val normalBitmap: Bitmap by lazy { ResourcesAccess.obtainBitmap(this.normalCharacters) }
    private val emphasisBitmap: Bitmap by lazy { ResourcesAccess.obtainBitmap(this.emphasisCharacters) }

    /**
     * Convert a text to draw information iterator
     */
    fun convert(stringText: String, factor: Int = 1): AlphabetDrawIterator
    {
        val string = stringText.trim()
        val factorControlled = max(1, factor).toFloat()
        val characterWidth = this.characterWidth * factorControlled.toInt()
        val characterHeight = this.characterHeight * factorControlled.toInt()

        // Emphasis detection
        val stringBuilder = StringBuilder()
        val emphasisQueue = Queue<Int>()
        var start = 0
        var delta = 0
        var index = string.indexOf("**")

        while (index >= 0)
        {
            emphasisQueue.enqueue(index - delta)
            stringBuilder.append(string.substring(start, index))
            delta += 2
            start = index + 2
            index = string.indexOf("**", start)
        }

        stringBuilder.append(string.substring(start))

        // Normalization to separate letter and accent
        val normalized =
            Normalizer.normalize(stringBuilder.toString(), Normalizer.Form.NFD).toCharArray()

        // Collect offsets and spaces
        val offsets = ArrayList<IntList>()
        index = 0
        var charactersOffset = IntList()
        var notTheFirst = false

        for (character in normalized)
        {
            if (character.code < 256)
            {
                if (character == ' ')
                {
                    offsets.add(charactersOffset)
                    charactersOffset = IntList()
                    continue
                }
                else if (notTheFirst)
                {
                    offsets.add(charactersOffset)
                    charactersOffset = IntList()
                }

                notTheFirst = true
                index++
            }

            charactersOffset += this.code(character)
        }

        if (charactersOffset.notEmpty)
        {
            offsets.add(charactersOffset)
        }

        // Create the characters
        val alphabetCharacters = ArrayList<AlphabetCharacter>()
        var emphasis = false

        for ((indexOffset, offset) in offsets.withIndex())
        {
            val isSpace = offset.empty

            if (emphasisQueue.notEmpty && indexOffset == emphasisQueue.peek())
            {
                emphasis = !emphasis
                emphasisQueue.dequeue()
            }

            val endOfWorld = indexOffset + 1 < offsets.size && offsets[indexOffset + 1].empty
            val widthWorld =
                if (isSpace)
                {
                    1
                }
                else if (endOfWorld)
                {
                    Alphabet.worldWidth(indexOffset + 2, offsets)
                }
                else
                {
                    Alphabet.worldWidth(indexOffset, offsets)
                }

            alphabetCharacters.add(AlphabetCharacter(endOfWorld,
                                                     widthWorld * this.characterWidth * factor,
                                                     isSpace, emphasis,
                                                     offset, factorControlled, this))
        }

        return AlphabetDrawIterator(characterWidth, characterHeight, alphabetCharacters.iterator())
    }

    internal fun draw(emphasis: Boolean, characterOffset: Int, factor: Float,
                      x: Float, y: Float,
                      canvas: Canvas, paint: Paint)
    {
        if (characterOffset < 0 || characterOffset >= this.charactersOrder.size)
        {
            return
        }

        val deltaY =
            if (this.charactersOrder[characterOffset] == COMBINING_TILDE) this.tildeDeltaY * factor else 0f

        val baseImage = if (emphasis) this.emphasisBitmap else this.normalBitmap
        val xx = (characterOffset % this.numberCharactersPerLine) * this.characterWidth
        val yy = (characterOffset / this.numberCharactersPerLine) * this.characterHeight

        canvas.drawBitmap(baseImage,
                          Rect(xx, yy + 1,
                               xx + this.characterWidth,
                               yy + this.characterHeight),
                          RectF(x, y + deltaY,
                                x + this.characterWidth * factor,
                                y + this.characterHeight * factor),
                          paint)
    }

    private fun code(character: Char): Int
    {
        return this.charactersOrder.indexOf(character)
    }
}
