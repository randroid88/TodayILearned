package io.winf.todayilearned.utils

import io.winf.todayilearned.Entry
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.*
import org.mockito.Mockito.*

class EntryCreatorTest {

    @Mock
    lateinit var mockDateTimeFetcher: DateTimeFetcher

    lateinit var entryCreator: EntryCreator
    lateinit var expectedEntryText: String
    lateinit var actualEntry: Entry

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun entryCreated() {
        givenEntryCreator()
        givenTextIs(TestArgGenerator.anyString())
        val now = System.currentTimeMillis()
        givenDateTimeIs(now)

        whenEntryCreated(entryCreator, expectedEntryText)

        thenEntryTextIsAsSet()
        thenEntryDateTimeIs(now)
    }

    private fun givenEntryCreator() {
        entryCreator = EntryCreator(mockDateTimeFetcher)
    }

    private fun givenTextIs(entryText: String) {
        expectedEntryText = entryText
    }

    private fun givenDateTimeIs(dateTime: Long) {
        `when`(mockDateTimeFetcher.now()).thenReturn(dateTime)
    }

    private fun whenEntryCreated(entryCreator: EntryCreator, entryText: String) {
        actualEntry = entryCreator.create(entryText)
    }

    private fun thenEntryDateTimeIs(now: Long) {
        assertEquals(now, actualEntry.entryDateTime)
    }

    private fun thenEntryTextIsAsSet() {
        assertEquals(expectedEntryText, actualEntry.entryText)
    }
}
