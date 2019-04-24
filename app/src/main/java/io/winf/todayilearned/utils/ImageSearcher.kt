package io.winf.todayilearned.utils

import android.content.Intent

class ImageSearcher {
    fun getIntent(): Intent {
        return Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }
    }
}
