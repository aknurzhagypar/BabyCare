package kz.aknur.newchildcare.content.surveys.survey

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_survey.*
import kotlinx.android.synthetic.main.fragment_survey_start.surveyStartFragLv
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.surveys.models.SurveyStartModel
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.toast


class SurveyFragment() : Fragment() {


    private val adapter: SurveyAdapter = SurveyAdapter(this)

    private var tempAnswerList: JsonArray = JsonArray()

    private var groupId: Int? = null
    private var mouth: Int? = null
    private var groupName: String? = null
    private var surveyId: Int? = null

    companion object {
        @JvmStatic
        fun newInstance(groupId: Int, mouth: Int, groupName: String) = SurveyFragment().apply {
            arguments = Bundle().apply {
                putSerializable("groupId", groupId)
                putSerializable("mouth", mouth)
                putSerializable("groupName", groupName)
            }
        }

        const val TAG = "SurveyFragment"
    }

    private lateinit var viewModel: SurveyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            groupId = it.getSerializable("groupId") as Int
            mouth = it.getSerializable("mouth") as Int
            groupName = it.getSerializable("groupName") as String
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_survey, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        setLoading(true)
        viewModel = ViewModelProvider(this).get(SurveyViewModel::class.java)
        observer()
        prepareSurveyStart()
        setupListeners()
        setLoading(false)
    }

    private fun setupListeners() {
        surveyRV.adapter = adapter
        surveyConfirm.onClick {
            if (tempAnswerList.size()>0) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.sendAnswer(surveyId!!, tempAnswerList)
                }
            }else{
                toast("Вы не ответили на вопросы")
            }
        }
    }

    fun addNewAnswer(questionId: Int, answer: String) {
        var isHas = false
        val newAnswer = JsonObject()
        newAnswer.addProperty("answer", answer)
        newAnswer.addProperty("questionId", questionId)

        if (tempAnswerList.size() > 0) {
            for (i in 0 until tempAnswerList.size()) {
                if (tempAnswerList.get(i).asJsonObject?.get("questionId")
                        .toString() == questionId.toString()
                ) {
                    isHas = true
                    tempAnswerList.set(i, newAnswer)
                }
            }
        }

        if (!isHas) {
            tempAnswerList.add(newAnswer)
        }

        Log.d(TAG, tempAnswerList.toString())

    }

    private fun prepareSurveyStart() {
        surveyTitleTv.text = groupName.toString()
        startSurvey(mouth!!.toInt())
    }

    private fun startSurvey(month: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.startSurvey(month, groupId!!)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observer() {
        viewModel.isError.observe(viewLifecycleOwner, Observer {
            errorDialog(it)
        })

        viewModel.surveyQuestions.observe(viewLifecycleOwner, Observer {
            showQuestions(it)
        })

        viewModel.isSended.observe(viewLifecycleOwner, Observer {
            if (it){
                toast("Ваши ответы сохранены, спасибо!")
                activity?.finish()
            }
        })

    }

    private fun showQuestions(it: SurveyStartModel) {
        surveyId = it.id
        adapter.addQuestions(it.questions)
        setLoading(false)
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
        if (b) surveyStartFragLv.visibility = View.VISIBLE else surveyStartFragLv.visibility =
            View.GONE
    }

}