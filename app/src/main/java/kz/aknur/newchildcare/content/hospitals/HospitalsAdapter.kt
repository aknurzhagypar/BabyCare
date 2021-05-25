package kz.aknur.newchildcare.content.hospitals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.hospitals.models.HospitalModel
import kotlin.collections.ArrayList

class HospitalsAdapter : RecyclerView.Adapter<HospitalsAdapter.HospitalsHolder> {

    companion object {
        const val TAG = "HospitalsAdapter"
    }

    private var hospitalList: ArrayList<HospitalModel> = ArrayList()
    private var callback: HospitalsFragment

    constructor(callback: HospitalsFragment) : super() {
        this.callback = callback
    }

    fun addHospitals(hospitalList: List<HospitalModel>) {
        this.hospitalList.addAll(hospitalList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HospitalsHolder {
        val root: View =
            LayoutInflater.from(parent.context).inflate(R.layout.hospital_item, parent, false)
        return HospitalsHolder(root)
    }

    override fun getItemCount(): Int {
        return hospitalList.count()
    }

    override fun onBindViewHolder(holder: HospitalsHolder, position: Int) {
        holder.bind(hospitalList.get(position), callback)
    }

    class HospitalsHolder(private val root: View) :
        RecyclerView.ViewHolder(root) {
        private val hospitalIcon: ImageView = root.findViewById(R.id.hospitalIcon)
        private val hospName: TextView = root.findViewById(R.id.hospName)
        private val hospText: TextView = root.findViewById(R.id.hospText)
        private val hospAddress: TextView = root.findViewById(R.id.hospAddress)

        fun bind(
            hospitalList: HospitalModel,
            callback: HospitalsFragment
        ) {
            hospName.text = hospitalList.name
//            hospText.text = hospitalList.info
            hospAddress.text = hospitalList.address

//            if (hospitalList.icon != null) {
//
//               Glide.with(root)
//                    .load(hospitalList.icon)
//                    .placeholder(R.drawable.loader)
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
//                    .into(hospitalIcon)
//                hospitalIcon.visibility = View.VISIBLE
//            }
            root.setOnClickListener { v: View? ->
                callback.onHospitalClick(
                    hospitalList
                )
            }
        }

    }

}

