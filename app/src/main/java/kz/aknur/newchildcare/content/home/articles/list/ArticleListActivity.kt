package kz.aknur.newchildcare.content.home.articles.list

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_article_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.home.articles.details.ArticleActivity
import kz.aknur.newchildcare.content.home.categories.models.SmallCategoriesModel
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick

class ArticleListActivity : AppCompatActivity() {

    private var needCategory: Int? = null

    private val adapterS: CategoriesAdapter = CategoriesAdapter(this)
    private val articlesAdapter: ArticlesAdapter = ArticlesAdapter(this)
    private lateinit var viewModel: ArticlesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)
        lets()
    }

    private fun lets() {
        prepare()
        setupRecyclerView()
        setupListeners()
        getCategories()
        observer()
    }

    private fun prepare() {
        viewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
        if (intent.hasExtra("categoryId")) {
            needCategory = intent.getIntExtra("categoryId", 0)
            getArticles(needCategory!!)
        }
    }

    fun onSmallCategoryClick(articlesModel: SmallCategoriesModel) {
        getArticles(articlesModel.id)
    }


    private fun setupRecyclerView() {
        categoriesRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        categoriesRv.adapter = adapterS
        articlesRv.adapter = articlesAdapter
    }

    private fun getCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getCategories()
        }
    }

    private fun getArticles(needCategory: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getArticles(needCategory)
        }
    }

    private fun observer() {
        viewModel.isError.observe(this, Observer {
            errorDialog(it)
        })

        viewModel.categories.observe(this, Observer {
            if (it != null) {
                addSmallCat(it)
            } else {
                errorDialog(getString(R.string.error_unknown_body))
            }
        })

        viewModel.articles.observe(this, Observer {
            if (it != null) {
                if (it.isEmpty()){
                    empty_article_list.isVisible = true
                }else{
                    empty_article_list.isVisible = false
                    addArticles(it)
                }
            } else {
                errorDialog(getString(R.string.error_unknown_body))
            }
        })
    }


    private fun addSmallCat(scList: List<SmallCategoriesModel>) {
        adapterS.addSmallCat(scList)
    }

    fun onArticleClick(articlesModel: ArticlesModel) {
        Log.d("TESTART", articlesModel.toString())
        startActivity(
            intentFor<ArticleActivity>().putExtra(
                "articleId",
                articlesModel.id.toString()
            )
        )
    }

    override fun onResume() {
        super.onResume()
        if (intent.hasExtra("categoryId")) {
            needCategory = intent.getIntExtra("categoryId", 0)
            getArticles(needCategory!!)
        }
    }

    private fun addArticles(it: List<ArticlesModel>) {
        articlesAdapter.addArticles(it)
    }

    private fun setupListeners() {
        back_button.onClick {
            finish()
        }
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
                finish()
            }
        }.show()
    }


}