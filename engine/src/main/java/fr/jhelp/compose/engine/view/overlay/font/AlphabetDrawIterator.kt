package fr.jhelp.compose.engine.view.overlay.font

/**
 * Iterator for draw an alphabet string
 *
 * @property characterWidth Each character width
 * @property characterHeight Each character height
 */
class AlphabetDrawIterator internal constructor(val characterWidth: Int, val characterHeight: Int,
                                                private val iterator: Iterator<AlphabetCharacter>) :
        Iterator<AlphabetCharacter>
{
    override fun hasNext(): Boolean = this.iterator.hasNext()

    override fun next(): AlphabetCharacter = this.iterator.next()
}
