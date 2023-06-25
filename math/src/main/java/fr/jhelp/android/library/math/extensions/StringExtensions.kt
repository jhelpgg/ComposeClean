package fr.jhelp.android.library.math.extensions

/**
 * Compare ignore case, and if same, compare with take care case
 */
fun String.compareAlphabet(string: String): Int
{
    val comparison = this.compareTo(string, true)

    if (comparison != 0)
    {
        return comparison
    }

    return this.compareTo(string, false)
}
