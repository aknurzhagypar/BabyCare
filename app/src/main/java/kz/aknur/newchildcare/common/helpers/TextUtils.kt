package kz.aknur.newchildcare.common.helpers

import android.os.Build
import android.text.Html
import android.text.Spanned

object TextUtils {
    fun textToHtml(text: String?): Spanned? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
        } else {
            Html.fromHtml(text)
        }
    }
}