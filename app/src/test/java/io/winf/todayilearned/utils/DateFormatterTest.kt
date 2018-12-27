package io.winf.todayilearned.utils

import org.junit.Assert
import org.junit.Test

class DateFormatterTest {

    private var dateFormatter: DateFormatter = DateFormatter()
    private var actualDate: String = ""

    @Test
    fun formatTimeMin() {
        val givenDateTime = 0L
        whenFormatDate(givenDateTime)
        thenFormattedDateIs("12/31/1969")
    }

    @Test
    fun formatTimeMax() {
        val givenDateTime = Long.MAX_VALUE
        whenFormatDate(givenDateTime)
        thenFormattedDateIs("08/16/292278994")
    }

    private fun whenFormatDate(unixDateTime: Long) {
        actualDate = dateFormatter.formatDate(unixDateTime)
    }

    private fun thenFormattedDateIs(expected: String) {
        Assert.assertEquals(expected, actualDate)
    }

}