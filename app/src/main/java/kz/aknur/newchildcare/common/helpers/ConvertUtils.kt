package kz.aknur.newchildcare.common.helpers

import android.content.Context
import android.util.DisplayMetrics

object ConvertUtils {
    fun millisToSeconds(millis: Long): Int {
        return Math.round(millis / 1000.0f)
    }

    fun secondsToMinutes(seconds: Long): Int {
        return Math.round(seconds / 60.0f)
    }

    fun metersToKilometers(meters: Double): Int {
        return Math.round(meters / 1000.0).toInt()
    }

    fun dpToPx(dp: Float, context: Context): Float {
        return dp * (context.resources
            .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun pxToDp(px: Int, context: Context): Float {
        return px / (context.resources
            .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}