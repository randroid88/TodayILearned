package io.winf.todayilearned.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {
    fun formatDate(unixDateTime: Long): String {
        val sdf = SimpleDateFormat("MM/dd/yyyy", Locale.US)
        val date = Date(unixDateTime)
        return sdf.format(date)
    }
}
