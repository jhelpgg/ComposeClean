package fr.jhelp.android.library.math.extensions

import org.junit.Assert
import org.junit.Test

class IntExtensionsTests
{
    @Test
    fun boundsTest()
    {
        Assert.assertEquals(3, 1.bounds(3, 7))
        Assert.assertEquals(3, 3.bounds(3, 7))
        Assert.assertEquals(4, 4.bounds(3, 7))
        Assert.assertEquals(7, 7.bounds(3, 7))
        Assert.assertEquals(7, 9.bounds(3, 7))

        Assert.assertEquals(3, 1.bounds(7, 3))
        Assert.assertEquals(3, 3.bounds(7, 3))
        Assert.assertEquals(4, 4.bounds(7, 3))
        Assert.assertEquals(7, 7.bounds(7, 3))
        Assert.assertEquals(7, 9.bounds(7, 3))
    }
}