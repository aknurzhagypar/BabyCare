package kz.aknur.newchildcare.common.helpers

import android.app.Activity
import android.view.WindowManager

object GeneralHelper  {

    fun hideStatusBar(activity: Activity){
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}