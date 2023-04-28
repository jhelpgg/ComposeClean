package fr.jhelp.compose.collection

/**
 * Comparator for comparable and use their "natural" order
 */
class NaturalComparator<C : Comparable<C>> : Comparator<C>
{
    /**
     * Compare two comparable
     */
    override fun compare(comparable1: C, comparable2: C): Int =
        comparable1.compareTo(comparable2)
}