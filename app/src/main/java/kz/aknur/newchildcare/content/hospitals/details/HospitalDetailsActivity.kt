package kz.aknur.newchildcare.content.hospitals.details

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_hospital_details.*
import kz.aknur.newchildcare.R
import org.jetbrains.anko.sdk27.coroutines.onClick

class HospitalDetailsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital_details)
        lets()
    }

    private fun lets() {
        initHospital()
        setupListeners()
    }

    private fun initHospital() {
        hospital_name.text = intent.getStringExtra("name")
        hospital_name_2.text = intent.getStringExtra("name")
        hospital_address.text = intent.getStringExtra("address")
        hospital_address_2.text = intent.getStringExtra("address")
        hospital_info.text = intent.getStringExtra("info")
    }

    private fun setupListeners() {
        hospital_back_button.onClick {
            finish()
        }
        hospital_services.onClick {
            Toast.makeText(this@HospitalDetailsActivity, "Для данной организации ещё не заполнен список услуг", Toast.LENGTH_SHORT).show()
        }
    }


}