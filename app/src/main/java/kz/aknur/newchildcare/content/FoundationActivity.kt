package kz.aknur.newchildcare.content

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_foundation.*
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.calendar.CalendarFragment
import kz.aknur.newchildcare.content.favorite_articles.FavoriteArticlesFragment
import kz.aknur.newchildcare.content.home.HomeFragment
import kz.aknur.newchildcare.content.hospitals.HospitalsFragment
import kz.aknur.newchildcare.content.profile.ProfileFragment

class FoundationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foundation)
        lets()
    }

    private fun lets() {
        mainMenuInit()
    }

    private fun mainMenuInit() {
        loadFragment(HomeFragment.newInstance())
        mainBottomMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment.newInstance())
                R.id.nav_calendar -> loadFragment(CalendarFragment.newInstance())
                R.id.nav_hospitals -> loadFragment(HospitalsFragment.newInstance())
                R.id.nav_articles -> loadFragment(FavoriteArticlesFragment.newInstance())
                R.id.nav_profile -> loadFragment(ProfileFragment.newInstance())
                else -> loadFragment(HomeFragment.newInstance())
            }
        }
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.foundationContainer, fragment)
            commit()
        }
        return true
    }


}