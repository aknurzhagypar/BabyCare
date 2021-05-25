package kz.aknur.newchildcare.content.hospitals.map

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.image.ImageProvider
import kotlinx.android.synthetic.main.activity_hospital_map.*
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.hospitals.models.HospitalModel
import org.jetbrains.anko.sdk27.coroutines.onClick


class HospitalMapActivity: AppCompatActivity() {

    override fun onStop() {
        super.onStop()
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onStart() {
        super.onStart()
        mapview.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey("948c76e1-c753-4415-8219-6a0580ecdc9b")
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_hospital_map)
        lets()

    }

    private fun lets() {
        initMap()
        setupListeners()
        initHospitals()
    }

    private fun initHospitals() {
        val hospitalList = intent.getSerializableExtra("list") as List<HospitalModel>
        for(i in hospitalList.indices){
            Log.d("addd", "added: " + hospitalList[i].lat + "   " + hospitalList[i].lon)
            mapview.map.mapObjects.addPlacemark(
                Point(hospitalList[i].lat, hospitalList[i].lon),
                ImageProvider.fromResource(
                    this,
                    R.drawable.ic_baseline_add_circle_24
                ))
        }
    }

    private fun setupListeners() {
        hospital_map_back_button.onClick {
            finish()
        }
    }

    private fun initMap() {
        mapview.map.move(
            CameraPosition(Point(43.237156, 76.945618), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0f),
            null
        )
    }
}