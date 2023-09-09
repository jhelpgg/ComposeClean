package fr.jhelp.android.library.tasks.future

import fr.jhelp.android.library.tasks.extensions.parallel
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import org.junit.Assert
import org.junit.Test

/**
 * Futures tests.
 */
class FuturesTests
{
    @Test
    fun simpleSucceedFuture()
    {
        val promise = Promise<String>()
        val future = promise.future
        Assert.assertEquals(FutureResultStatus.COMPUTING, future.status())
        val result = AtomicReference<String>("")
        val waiting = AtomicBoolean(true)
        val error = AtomicReference<Exception>(null)
        val error2 = AtomicReference<Exception>(null)
        val cancelReason = AtomicReference<String>("")
        ({
            try
            {
                result.set(future())
            }
            catch (exception: Exception)
            {
                error.set(exception)
            }
        }).parallel()
        ({
            future.waitComplete()
            waiting.set(false)
        }).parallel()
        future.onError { exception -> error2.set(exception) }
        future.onCancel { reason -> cancelReason.set(reason) }
        Thread.sleep(256)
        Assert.assertEquals("", result.get())
        Assert.assertTrue(waiting.get())
        Assert.assertNull(error.get())
        Assert.assertNull(error2.get())
        Assert.assertEquals("", cancelReason.get())
        promise.result("hello")
        future.waitComplete()
        Assert.assertEquals(FutureResultStatus.SUCCEED, future.status())
        Thread.sleep(256)
        Assert.assertEquals("hello", result.get())
        Assert.assertFalse(waiting.get())
        Assert.assertNull(error.get())
        Assert.assertNull(error2.get())
        Assert.assertEquals("", cancelReason.get())
    }

    @Test
    fun simpleFailureFuture()
    {
        val promise = Promise<String>()
        val future = promise.future
        Assert.assertEquals(FutureResultStatus.COMPUTING, future.status())
        val result = AtomicReference<String>("")
        val waiting = AtomicBoolean(true)
        val error = AtomicReference<Exception>(null)
        val error2 = AtomicReference<Exception>(null)
        val cancelReason = AtomicReference<String>("")
        ({
            try
            {
                result.set(future())
            }
            catch (exception: Exception)
            {
                error.set(exception)
            }
        }).parallel()
        ({
            future.waitComplete()
            waiting.set(false)
        }).parallel()
        future.onError { exception -> error2.set(exception) }
        future.onCancel { reason -> cancelReason.set(reason) }
        Thread.sleep(256)
        Assert.assertEquals("", result.get())
        Assert.assertTrue(waiting.get())
        Assert.assertNull(error.get())
        Assert.assertNull(error2.get())
        Assert.assertEquals("", cancelReason.get())
        promise.error(Exception("By purpose"))
        future.waitComplete()
        Assert.assertEquals(FutureResultStatus.FAILED, future.status())
        Thread.sleep(256)
        Assert.assertEquals("", result.get())
        Assert.assertFalse(waiting.get())
        Assert.assertNotNull(error.get())
        Assert.assertEquals("By purpose", error.get().message)
        Assert.assertNotNull(error2.get())
        Assert.assertEquals("By purpose", error2.get().message)
        Assert.assertEquals("", cancelReason.get())
    }

    @Test
    fun simpleCancelFuture()
    {
        val promise = Promise<String>()
        val future = promise.future
        Assert.assertEquals(FutureResultStatus.COMPUTING, future.status())
        val result = AtomicReference<String>("")
        val waiting = AtomicBoolean(true)
        val error = AtomicReference<Exception>(null)
        val error2 = AtomicReference<Exception>(null)
        val cancelReason = AtomicReference<String>("")
        ({
            try
            {
                result.set(future())
            }
            catch (exception: Exception)
            {
                error.set(exception)
            }
        }).parallel()
        ({
            future.waitComplete()
            waiting.set(false)
        }).parallel()
        future.onError { exception -> error2.set(exception) }
        future.onCancel { reason -> cancelReason.set(reason) }
        Thread.sleep(256)
        Assert.assertEquals("", result.get())
        Assert.assertTrue(waiting.get())
        Assert.assertNull(error.get())
        Assert.assertNull(error2.get())
        Assert.assertEquals("", cancelReason.get())
        future.cancel("Cancel for test")
        future.waitComplete()
        Assert.assertEquals(FutureResultStatus.CANCELED, future.status())
        Thread.sleep(256)
        Assert.assertEquals("", result.get())
        Assert.assertFalse(waiting.get())
        Assert.assertNotNull(error.get())
        Assert.assertTrue(error.get() is CancellationException)
        Assert.assertEquals("Cancelled because : Cancel for test", error.get().message)
        Assert.assertNotNull(error2.get())
        Assert.assertTrue(error2.get() is CancellationException)
        Assert.assertEquals("Cancelled because : Cancel for test", error2.get().message)
        Assert.assertEquals("Cancel for test", cancelReason.get())
    }

    @Test
    fun andTest()
    {
        val promise = Promise<String>()
        val result = AtomicInteger(-1)
        val future = promise.future
            .and { string -> string.toInt() }
            .and { integer -> result.set(integer) }
        promise.result("42")
        future.waitComplete()
        Assert.assertEquals(FutureResultStatus.SUCCEED, future.status())
        Assert.assertEquals(42, result.get())
    }

    @Test
    fun andTestWithError()
    {
        val promise = Promise<String>()
        val result = AtomicInteger(-1)
        val future = promise.future
            .and { string -> string.toInt() }
            .and { integer -> result.set(integer) }
        promise.result("Hello")
        future.waitComplete()
        Assert.assertEquals(FutureResultStatus.FAILED, future.status())
        Assert.assertEquals(-1, result.get())
    }

    @Test
    fun andIfTest()
    {
        val promise = Promise<Int>()
        val result = AtomicInteger(-1)
        val future = promise.future
            .andIf({ value -> value > 10 }) { value -> result.set(value) }
        promise.result(73)
        future.waitComplete()
        Assert.assertEquals(73, result.get())
    }

    @Test
    fun andIfNotMatchTest()
    {
        val promise = Promise<Int>()
        val result = AtomicInteger(-1)
        val future = promise.future
            .andIf({ value -> value > 10 }) { value -> result.set(value) }
        promise.result(5)
        future.waitComplete()
        Assert.assertEquals(-1, result.get())
    }


    @Test
    fun andUnwrappedTest()
    {
        val promise = Promise<String>()
        val result = AtomicInteger(-1)
        val future = promise.future
            .andUnwrap { string -> string.toInt().future() }
            .and { integer -> result.set(integer) }
        promise.result("42")
        future.waitComplete()
        Assert.assertEquals(FutureResultStatus.SUCCEED, future.status())
        Assert.assertEquals(42, result.get())
    }

    @Test
    fun andIfUnwrappedTest()
    {
        val promise = Promise<Int>()
        val result = AtomicInteger(-1)
        val future = promise.future
            .andIfUnwrap({ value -> value > 10 }) { value -> result.set(value).future() }
        promise.result(73)
        future.waitComplete()
        Assert.assertEquals(73, result.get())
    }

    @Test
    fun thenTest()
    {
        val promise = Promise<Int>()
        val result = AtomicInteger(-1)
        val future = promise.future
            .then { futureResult ->
                when (futureResult.status())
                {
                    FutureResultStatus.COMPUTING -> -1
                    FutureResultStatus.FAILED    -> 33
                    FutureResultStatus.CANCELED  -> 22
                    FutureResultStatus.SUCCEED   -> futureResult() + 31
                }
            }
            .and { value -> result.set(value) }
        promise.result(42)
        future.waitComplete()
        Assert.assertEquals(73, result.get())
    }


    @Test
    fun thenUnwrappedTest()
    {
        val promise = Promise<Int>()
        val result = AtomicInteger(-1)
        val future = promise.future
            .thenUnwrap { futureResult ->
                when (futureResult.status())
                {
                    FutureResultStatus.COMPUTING -> -1
                    FutureResultStatus.FAILED    -> 33
                    FutureResultStatus.CANCELED  -> 22
                    FutureResultStatus.SUCCEED   -> futureResult() + 31
                }.future()
            }
            .and { value -> result.set(value) }
        promise.result(42)
        future.waitComplete()
        Assert.assertEquals(73, result.get())
    }
}