package kz.aknur.newchildcare.content.surveys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_base_surveys.*
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.surveys.groups.SurveyGroupsFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class BaseSurveysActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_surveys)
        lets()
    }

    private fun lets() {
        surveysBaseBackButton.onClick { finish() }
        loadFragment(SurveyGroupsFragment.newInstance())
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.surveysContainer, fragment)
            commit()
        }
        return true
    }
}