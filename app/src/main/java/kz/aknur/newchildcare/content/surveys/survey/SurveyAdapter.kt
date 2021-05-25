package kz.aknur.newchildcare.content.surveys.survey

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.surveys.models.SurveyQuestionsModel
import kotlin.collections.ArrayList

class SurveyAdapter : RecyclerView.Adapter<SurveyAdapter.SurveyHolder> {

    companion object {
        const val TAG = "SurveyAdapter"
    }

    var questionsCount: Int = 1

    private var questions: ArrayList<SurveyQuestionsModel> = ArrayList()
    private var callback: SurveyFragment

    constructor(callback: SurveyFragment) : super() {
        this.callback = callback
    }

    fun addQuestions(questions: List<SurveyQuestionsModel>) {
        this.questions.addAll(questions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SurveyHolder {
        val root: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_survey_questions, parent, false)
        return SurveyHolder(root)
    }

    override fun getItemCount(): Int {
        return questions.count()
    }

    override fun onBindViewHolder(holder: SurveyHolder, position: Int) {
        holder.bind(questions[position], callback, questionsCount)
        questionsCount += 1
    }

    class SurveyHolder(private val root: View) :
        RecyclerView.ViewHolder(root) {
        private val questionTitle: TextView = root.findViewById(R.id.surveyQuestion)
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        private val answer: Switch = root.findViewById(R.id.surveySwitch)

        @SuppressLint("SetTextI18n")
        fun bind(
            questions: SurveyQuestionsModel,
            callback: SurveyFragment,
            questionsCount: Int
        ) {
            questionTitle.text = questionsCount.toString() +". " + questions.text
            answer.setOnClickListener {
                if (answer.isSelected) {
                    answer.text = "Нет"
                    callback.addNewAnswer(questions.id, "NO")
                    answer.isSelected = false
                }else{
                    answer.text = "Да"
                    answer.isSelected = true
                    callback.addNewAnswer(questions.id, "YES")
                }
            }
//            root.setOnClickListener { v: View? ->
//                callback.onQuestionClick(
//                    surveyGroup
//                )
//            }
        }

    }

}

