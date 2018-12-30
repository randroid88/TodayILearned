package io.winf.todayilearned.utils

import org.junit.Assert.*
import org.junit.Test

class DateTimeFetcherTest {

    var actualDateTime: Long = 0L

    @Test
    fun now() {
        val expectedDateTime: Long = givenCurrentTime()

        whenNowFetched()

        thenNowIsCloseTo(expectedDateTime, 10L)
    }

    private fun givenCurrentTime() = System.currentTimeMillis()

    private fun whenNowFetched() {
        actualDateTime = DateTimeFetcher().now()
    }

    private fun thenNowIsCloseTo(expectedDateTime: Long, variance: Long) {
        if (Math.abs(expectedDateTime - actualDateTime) > variance) {
            fail("dateTime was $actualDateTime expected dateTime was $expectedDateTime with a variance +/- $variance")
        }
    }
}