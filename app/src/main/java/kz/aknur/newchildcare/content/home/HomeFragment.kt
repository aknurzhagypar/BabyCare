package kz.aknur.newchildcare.content.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.home.articles.list.ArticleListActivity
import kz.aknur.newchildcare.content.home.categories.LargeCategoriesAdapter
import kz.aknur.newchildcare.content.home.categories.SmallCategoriesAdapter
import kz.aknur.newchildcare.content.home.categories.models.LargeCategoriesModel
import kz.aknur.newchildcare.content.home.categories.models.SmallCategoriesModel
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.intentFor


class HomeFragment : Fragment() {


    private val adapterL: LargeCategoriesAdapter = LargeCategoriesAdapter(this)
    private val adapterS: SmallCategoriesAdapter = SmallCategoriesAdapter(this)

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()

        const val TAG = "HomeFragment"
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        setLoading(true)
        setupRecyclerView()
        setupListeners()
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        preparePage()
        getCategories()
        observer()
    }

    private fun setupListeners() {
        victimBtn.setOnClickListener {
            val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
            val viewGroup: ViewGroup = requireActivity().findViewById(android.R.id.content)
            val dialogView: View =
                LayoutInflater.from(activity?.applicationContext).inflate(R.layout.dialog_violence, viewGroup, false)
            builder.setView(dialogView)
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }
    }

    private fun setupRecyclerView() {
        smallBannerRv.layoutManager = GridLayoutManager(activity?.applicationContext, 4)
//        smallBannerRv.layoutManager = LinearLayoutManager(
//            activity,
//            LinearLayoutManager.HORIZONTAL,
//            false
//        )
        largeBannerRv.adapter = adapterL
        smallBannerRv.adapter = adapterS
    }

    private fun addLargeCat(lcList: List<LargeCategoriesModel>) {
        adapterL.addLargeCat(lcList)
        setLoading(false)
    }

    private fun addSmallCat(scList: List<SmallCategoriesModel>) {
        adapterS.addSmallCat(scList)
        setLoading(false)
    }

    private fun getCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getMainCategories()
        }
    }

    private fun preparePage() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getCity()
        }
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getName()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observer() {
        viewModel.isError.observe(viewLifecycleOwner, Observer {
            errorDialog(it)
        })

        viewModel.userCity.observe(viewLifecycleOwner, Observer {
            hfCountryTv.text = "Казахстан, $it"
        })

        viewModel.userName.observe(viewLifecycleOwner, Observer {
            hfNameTv.text = "Здравствуйте, $it!"
        })

        viewModel.largeCategories.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                addLargeCat(it)
                setLoading(false)
            } else {
                errorDialog(getString(R.string.error_unknown_body))
            }
        })

        viewModel.smallCategories.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                addSmallCat(it)
                setLoading(false)
            } else {
                errorDialog(getString(R.string.error_unknown_body))
            }
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
        if (b) homeFragLv.visibility = View.VISIBLE else homeFragLv.visibility = View.GONE
    }

    fun onLargeCategoryClick(lc: LargeCategoriesModel) {
        //
    }

    fun onSmallCategoryClick(lc: SmallCategoriesModel) {
        startActivity(intentFor<ArticleListActivity>().putExtra("categoryId", lc.id))
    }


}