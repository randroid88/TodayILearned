package io.winf.todayilearned.utils

import android.content.Intent
import org.junit.Test

import org.junit.Assert.*

class ImageSearcherTest {

    @Test
    fun getIntent() {
        val imageSearcher = givenImageSearcher()

        val result = whenGetIntent(imageSearcher)

        thenIntentHasOpenDocumentAction(result)
        thenIntentHasOneCategory(result)
        thenIntentCategoryIsOpenable(result)
        thenIntentTypeIsImage(result)
    }

    private fun givenImageSearcher() = ImageSearcher()

    private fun whenGetIntent(imageSearcher: ImageSearcher): Intent = imageSearcher.getIntent()

    private fun thenIntentHasOpenDocumentAction(intent: Intent) {
        assertEquals(Intent.ACTION_OPEN_DOCUMENT, intent.action)
    }

    private fun thenIntentHasOneCategory(intent: Intent) {
        assertEquals(1, intent.categories.size)
    }

    private fun thenIntentCategoryIsOpenable(intent: Intent) {
        assertTrue(intent.categories.contains(Intent.CATEGORY_OPENABLE))
    }

    private fun thenIntentTypeIsImage(intent: Intent) {
        assertEquals("image/*", intent.type)
    }
}