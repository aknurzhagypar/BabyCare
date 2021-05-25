package kz.aknur.newchildcare.content.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.child.list.ChildListActivity
import kz.aknur.newchildcare.content.home.articles.list.ArticleListActivity
import kz.aknur.newchildcare.content.home.categories.models.SmallCategoriesModel
import kz.aknur.newchildcare.content.surveys.BaseSurveysActivity
import kz.aknur.newchildcare.splash.SplashActivity
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.intentFor


class ProfileFragment : Fragment() {

    private val adapterS: CardCategoriesAdapter = CardCategoriesAdapter(this)


    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()

        const val TAG = "ProfileFragment"
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        //setLoading(true)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        preparePage()
        setupRecyclerView()
        getCategories()
        observer()
        listeners()
    }

    private fun getCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getCardCategories()
        }
    }

    private fun setupRecyclerView() {
        card_categories_rv.layoutManager = GridLayoutManager(activity?.applicationContext, 4)
        card_categories_rv.adapter = adapterS
    }

    private fun listeners() {
        setProfileBtn.onClick { confirmLogOut()  }
//        temp_start_survey_btn.onClick { startActivity(intentFor<BaseSurveysActivity>()) }
        my_child_list.onClick { startActivity(intentFor<ChildListActivity>()) }
    }


    private fun preparePage() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getProfileInfo()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observer() {
        viewModel.isError.observe(viewLifecycleOwner, Observer {
            errorDialog(it)
        })

        viewModel.profileText.observe(viewLifecycleOwner, Observer {
            profileText.text = it
            setLoading(false)
        })

        viewModel.cardCategories.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                addCardCat(it)
                setLoading(false)
            } else {
                errorDialog(getString(R.string.error_unknown_body))
            }
        })

    }

    private fun addCardCat(it: List<SmallCategoriesModel>?) {
        val tempList: ArrayList<SmallCategoriesModel> = ArrayList()
        if (it != null) {
            for(i in it.indices){
                tempList.add(it[i])
            }
        }

        tempList.add(
            SmallCategoriesModel(
            789,
            "SURVEYS",
            "Опрос про развитие ребенка",
            "null"
        )
        )

        adapterS.addSmallCat(tempList)
        setLoading(false)
    }

    fun onCategoryClick(lc: SmallCategoriesModel) {
        if(lc.id==789){
            startActivity(intentFor<BaseSurveysActivity>())
        }else {
            startActivity(intentFor<ArticleListActivity>().putExtra("categoryId", lc.id))
        }
    }

    private fun errorDialog(errorMsg: String) {
        alert {
            title = getString(R.string.error_unknown_title)
            message = errorMsg
            isCancelable = false
            positiveButton(getString(R.string.dialog_retry)) { dialog ->
                setLoading(false)
                dialog.dismiss()
            }
            negativeButton(getString(R.string.dialog_exit)) {
                activity?.finish()
            }
        }.show()
    }

    private fun confirmLogOut() {
        alert {
            title = "Вы уверены?"
            message = "Что хотите выйти из аккаунта?"
            isCancelable = true
            positiveButton("Отменить") { dialog ->
                dialog.dismiss()
            }
            negativeButton("Выйти") {
                viewModel.logOut()
                activity?.startActivity(intentFor<SplashActivity>())
                activity?.finish()
            }
        }.show()
    }

    private fun setLoading(b: Boolean) {
        if (b) profileFragLv.visibility = View.VISIBLE else profileFragLv.visibility = View.GONE
    }


}