package fr.jhelp.compose.math.extensions

import org.junit.Assert
import org.junit.Test

class FloatExtensionsTests
{
    @Test
    fun boundsTest()
    {
        Assert.assertEquals(3f, 1f.bounds(3f, 7f))
        Assert.assertEquals(3f, 3f.bounds(3f, 7f))
        Assert.assertEquals(4f, 4f.bounds(3f, 7f))
        Assert.assertEquals(7f, 7f.bounds(3f, 7f))
        Assert.assertEquals(7f, 9f.bounds(3f, 7f))

        Assert.assertEquals(3f, 1f.bounds(7f, 3f))
        Assert.assertEquals(3f, 3f.bounds(7f, 3f))
        Assert.assertEquals(4f, 4f.bounds(7f, 3f))
        Assert.assertEquals(7f, 7f.bounds(7f, 3f))
        Assert.assertEquals(7f, 9f.bounds(7f, 3f))
    }
}