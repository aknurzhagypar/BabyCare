package kz.aknur.newchildcare.content.surveys.start

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_survey_start.*
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.surveys.survey.SurveyFragment
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.alert


class SurveyStartFragment() : Fragment() {


    private var groupId: Int? = null
    private var groupName: String? = null

    companion object {
        @JvmStatic
        fun newInstance(groupId: Int, groupName: String) = SurveyStartFragment().apply {
            arguments = Bundle().apply {
                putSerializable("groupId", groupId)
                putSerializable("groupName", groupName)
            }
        }

        const val TAG = "SurveyStartFragment"
    }

    private lateinit var viewModel: SurveyStartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            groupId = it.getSerializable("groupId") as Int
            groupName = it.getSerializable("groupName") as String
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_survey_start, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        viewModel = ViewModelProvider(this).get(SurveyStartViewModel::class.java)
        observer()
        setupListeners()
        setLoading(false)
    }

    private fun setupListeners() {
        surveyNextStep.onClick {
            prepareSurveyStart()
        }
    }

    private fun prepareSurveyStart() {
        val month = inputChildAge.text.toString()
        if (month.isNotEmpty()){
            startSurvey(month.toInt())
        }
    }

    private fun startSurvey(month: Int) {
        loadFragment(SurveyFragment.newInstance(groupId!!, month, groupName!!))

    }

    @SuppressLint("SetTextI18n")
    private fun observer() {
        viewModel.isError.observe(viewLifecycleOwner, Observer {
            errorDialog(it)
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
        if (b) surveyStartFragLv.visibility = View.VISIBLE else surveyStartFragLv.visibility = View.GONE
    }

    private fun loadFragment(fragment: Fragment): Boolean {
        activity?.supportFragmentManager!!.beginTransaction().apply {
            replace(R.id.surveysContainer, fragment)
            commit()
        }
        return true
    }

}