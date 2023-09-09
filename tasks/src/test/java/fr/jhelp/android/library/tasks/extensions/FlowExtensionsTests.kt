package fr.jhelp.android.library.tasks.extensions

import fr.jhelp.android.library.tasks.future.FutureResultStatus
import java.util.concurrent.atomic.AtomicInteger
import java.util.regex.Pattern
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert
import org.junit.Test

/**
 * Flow extensions tests.
 *
 * @constructor Create flow extensions tests.
 */
class FlowExtensionsTests
{
    private val integerPattern = Pattern.compile("[0-9]+")

    @Test
    fun doWhenTest()
    {
        val flow = MutableStateFlow<String>("")
        val value = AtomicInteger(-1)
        val future =
            flow.doWhen(condition = { string -> this.integerPattern.matcher(string).matches() },
                        action = { string -> value.set(string.toInt()) })
        Assert.assertEquals(-1, value.get())
        flow.value = "42"
        val status = future.waitComplete()
        Assert.assertEquals(FutureResultStatus.SUCCEED, status)
        Assert.assertEquals(42, value.get())
        flow.value = "73"
        Thread.sleep(512)
        Assert.assertEquals(42, value.get())
    }

    @Test
    fun doWhenErrorTest()
    {
        val flow = MutableStateFlow<String>("")
        val value = AtomicInteger(-1)
        val future =
            flow.doWhen(condition = { string -> this.integerPattern.matcher(string).matches() },
                        action = { string -> value.set(string.toInt()) })
        Assert.assertEquals(-1, value.get())
        flow.value = "999999999999999999999999999999999999999999999999999999"
        val status = future.waitComplete()
        Assert.assertEquals(FutureResultStatus.FAILED, status)
        Assert.assertTrue("Error is not NumberFormatException, but :${future.error()}",
                          future.error() is NumberFormatException)
        Assert.assertEquals(-1, value.get())
        flow.value = "73"
        Thread.sleep(512)
        Assert.assertEquals(-1, value.get())
    }

    @Test
    fun doWhenCancel() {
        val flow = MutableStateFlow<String>("")
        val value = AtomicInteger(-1)
        val future =
            flow.doWhen(condition = { string -> this.integerPattern.matcher(string).matches() },
                        action = { string -> value.set(string.toInt()) })
        Assert.assertEquals(-1, value.get())
        future.cancel("Cancel")
        flow.value = "42"
        val status = future.waitComplete()
        Assert.assertEquals(FutureResultStatus.CANCELED, status)
        Assert.assertEquals("Cancel", future.cancelReason())
        Assert.assertEquals(-1, value.get())
        flow.value = "73"
        Thread.sleep(512)
        Assert.assertEquals(-1, value.get())
    }
}
