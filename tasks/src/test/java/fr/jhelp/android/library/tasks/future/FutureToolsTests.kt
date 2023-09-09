package fr.jhelp.android.library.tasks.future

import org.junit.Assert
import org.junit.Test

class FutureToolsTests
{
    @Test
    fun joinTest()
    {
        val promiseInt = Promise<Int>()
        val promiseString = Promise<String>()
        val promiseBoolean = Promise<Boolean>()
        val futureJoin =
            listOf(promiseInt.future, promiseString.future, promiseBoolean.future).join()
        Assert.assertEquals(FutureResultStatus.COMPUTING, futureJoin.status())
        promiseInt.result(42)
        promiseInt.future.waitComplete()
        Assert.assertEquals(FutureResultStatus.COMPUTING, futureJoin.status())
        promiseString.result("test")
        promiseString.future.waitComplete()
        Assert.assertEquals(FutureResultStatus.COMPUTING, futureJoin.status())
        promiseBoolean.result(true)
        futureJoin.waitComplete()
        Assert.assertEquals(FutureResultStatus.SUCCEED, futureJoin.status())
        val list = futureJoin()
        Assert.assertEquals(3, list.size)
        Assert.assertEquals(42, list[0]())
        Assert.assertEquals("test", list[1]())
        Assert.assertEquals(true, list[2]())
    }

    @Test
    fun joinCancelTest()
    {
        val promiseInt = Promise<Int>()
        val promiseString = Promise<String>()
        val promiseBoolean = Promise<Boolean>()
        val futureJoin =
            listOf(promiseInt.future, promiseString.future, promiseBoolean.future).join()
        Assert.assertEquals(FutureResultStatus.COMPUTING, futureJoin.status())
        promiseInt.result(42)
        promiseInt.future.waitComplete()
        Assert.assertEquals(FutureResultStatus.COMPUTING, futureJoin.status())
        futureJoin.cancel("For test")
        futureJoin.waitComplete()
        Thread.sleep(512)
        Assert.assertEquals(FutureResultStatus.CANCELED, futureJoin.status())
        Assert.assertEquals("For test", futureJoin.cancelReason())
        Assert.assertEquals(42, promiseInt.future())
        Assert.assertEquals("For test", promiseString.future.cancelReason())
        Assert.assertEquals("For test", promiseBoolean.future.cancelReason())
    }

    @Test
    fun futureErrorTest()
    {
        val future = Exception("Error for test").futureError<String>()
        Assert.assertEquals(FutureResultStatus.FAILED, future.status())
        Assert.assertEquals("Error for test", future.error()?.message)
    }

    @Test
    fun futureCancelTest()
    {
        val future = "Cancel for test".futureCancel<String>()
        Assert.assertEquals(FutureResultStatus.CANCELED, future.status())
        Assert.assertEquals("Cancel for test", future.cancelReason())
    }

    @Test
    fun combineTest()
    {
        val promiseFirst = Promise<Int>()
        val promiseSecond = Promise<Int>()
        val future = combine(promiseFirst.future, promiseSecond.future, {first, second -> first+second})
        Assert.assertEquals(FutureResultStatus.COMPUTING, future.status())
        promiseFirst.result(31)
        promiseFirst.future.waitComplete()
        Assert.assertEquals(FutureResultStatus.COMPUTING, future.status())
        promiseSecond.result(42)
        future.waitComplete()
        Assert.assertEquals(FutureResultStatus.SUCCEED, future.status())
        Assert.assertEquals(73, future())
    }
}