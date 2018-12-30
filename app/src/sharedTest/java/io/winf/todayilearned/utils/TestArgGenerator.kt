package io.winf.todayilearned.utils

import java.nio.charset.Charset
import java.util.*

object TestArgGenerator {
    fun anyString(): String {
        val array = ByteArray(7)
        Random().nextBytes(array)
        return String(array, Charset.forName("UTF-8"))
    }

    fun anyLong(): Long {
        return Random().nextLong()
    }
}