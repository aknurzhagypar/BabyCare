package kz.aknur.newchildcare.common.helpers

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

object DateUtils {
    fun parseDate(dateString: String?): Date? {
        return if (StringUtils.isValid(dateString)) {
            try {
                val dateFormat: DateFormat =
                    SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                dateFormat.parse(dateString)
            } catch (e: ParseException) {
                null
            }
        } else {
            null
        }
    }

    fun formatDate(date: Date?): String? {
        return if (date == null) {
            ""
        } else {
            val dateFormat: DateFormat =
                SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            dateFormat.format(date)
        }
    }

    fun formatTime(date: Date?): String? {
        return if (date == null) {
            ""
        } else {
            val dateFormat: DateFormat =
                SimpleDateFormat("HH:mm", Locale.getDefault())
            dateFormat.format(date)
        }
    }

    fun formatDateShort(date: Date?): String? {
        return if (date == null) {
            ""
        } else {
            val dateFormat: DateFormat =
                SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            dateFormat.format(date)
        }
    }

    fun formatDateTime(date: Date?): String? {
        return if (date == null) {
            ""
        } else {
            val dateFormat: DateFormat =
                SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
            dateFormat.format(date)
        }
    }

    fun optionalDifferenceInSeconds(
        left: Date?,
        right: Date?
    ): Int {
        return if (left != null && right != null) {
            val leftMillis = left.time
            val rightMillis = right.time
            ConvertUtils.millisToSeconds(abs(rightMillis - leftMillis))
        } else {
            0
        }
    }
}