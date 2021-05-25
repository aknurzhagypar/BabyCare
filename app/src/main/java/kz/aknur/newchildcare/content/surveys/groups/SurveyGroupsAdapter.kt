package kz.aknur.newchildcare.content.surveys.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.surveys.models.SurveyGroupsModel
import kotlin.collections.ArrayList

class SurveyGroupsAdapter : RecyclerView.Adapter<SurveyGroupsAdapter.SurveyGroupsHolder> {

    companion object {
        const val TAG = "SurveyGroupsAdapter"
    }

    private var surveysList: ArrayList<SurveyGroupsModel> = ArrayList()
    private var callback: SurveyGroupsFragment

    constructor(callback: SurveyGroupsFragment) : super() {
        this.callback = callback
    }

    fun addSurveyGroup(surveysList: List<SurveyGroupsModel>) {
        this.surveysList.addAll(surveysList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SurveyGroupsHolder {
        val root: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_surveys_group, parent, false)
        return SurveyGroupsHolder(root)
    }

    override fun getItemCount(): Int {
        return surveysList.count()
    }

    override fun onBindViewHolder(holder: SurveyGroupsHolder, position: Int) {
        holder.bind(surveysList.get(position), callback)
    }

    class SurveyGroupsHolder(private val root: View) :
        RecyclerView.ViewHolder(root) {
        private val hospName: TextView = root.findViewById(R.id.surveyGroupsName)

        fun bind(
            surveyGroup: SurveyGroupsModel,
            callback: SurveyGroupsFragment
        ) {
            hospName.text = surveyGroup.name

            root.setOnClickListener { v: View? ->
                callback.onSurveyClick(
                    surveyGroup
                )
            }
        }

    }

}

