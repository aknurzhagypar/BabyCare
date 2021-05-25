package kz.aknur.newchildcare.content.surveys.groups

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_survey_groups.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.surveys.models.SurveyGroupsModel
import kz.aknur.newchildcare.content.surveys.start.SurveyStartFragment
import org.jetbrains.anko.support.v4.alert


class SurveyGroupsFragment : Fragment() {


    private val adapter: SurveyGroupsAdapter = SurveyGroupsAdapter(this)

    companion object {
        @JvmStatic
        fun newInstance() = SurveyGroupsFragment()

        const val TAG = "HospitalsFragment"
    }

    private lateinit var viewModel: SurveyGroupsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_survey_groups, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        setLoading(true)
        setupRecyclerView()
        viewModel = ViewModelProvider(this).get(SurveyGroupsViewModel::class.java)
        preparePage()
        observer()
    }

    private fun setupRecyclerView() {
        surveyGroupsRv.adapter = adapter
    }

    private fun addSurveyGroups(surveyGroups: List<SurveyGroupsModel>) {
        adapter.addSurveyGroup(surveyGroups)
        setLoading(false)
    }

    private fun preparePage() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getSurveyGroups()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observer() {
        viewModel.isError.observe(viewLifecycleOwner, Observer {
            errorDialog(it)
        })

        viewModel.surveyGroups.observe(viewLifecycleOwner, Observer {
            addSurveyGroups(it)
        })


    }

    private fun errorDialog(errorMsg: String) {
        alert {
            title = getString(R.string.error_unknown_title)
            message = errorMsg
            isCancelable = false
            positiveButton(getString(R.string.dialog_retry)) { dialog ->
                dialog.dismiss()
            }
            negativeButton(getString(R.string.dialog_exit)) {
                activity?.finish()
            }
        }.show()
    }

    private fun setLoading(b: Boolean) {
        if (b) surveyGroupsFragLv.visibility = View.VISIBLE else surveyGroupsFragLv.visibility = View.GONE
    }

    fun onSurveyClick(surveyGroup: SurveyGroupsModel) {
        loadFragment(SurveyStartFragment.newInstance(surveyGroup.id, surveyGroup.name))
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        activity?.supportFragmentManager!!.beginTransaction().apply {
            replace(R.id.surveysContainer, fragment)
            commit()
        }
        return true
    }


}