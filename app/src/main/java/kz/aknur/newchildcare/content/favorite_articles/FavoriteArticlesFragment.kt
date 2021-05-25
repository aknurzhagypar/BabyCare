package kz.aknur.newchildcare.content.favorite_articles

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_likes.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.home.articles.details.ArticleActivity
import kz.aknur.newchildcare.content.home.articles.list.ArticlesModel
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.intentFor


class FavoriteArticlesFragment : Fragment() {

    private val articlesAdapter: FavouriteArticlesAdapter = FavouriteArticlesAdapter(this)
    private lateinit var viewModel: FavouriteArticlesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_likes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        viewModel = ViewModelProvider(this).get(FavouriteArticlesViewModel::class.java)
        setupRecyclerView()
        setupListeners()
        observer()
        getArticles()
    }

    private fun observer() {
        viewModel.isError.observe(viewLifecycleOwner, Observer {
            errorDialog(it)
        })

        viewModel.articles.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                addArticles(it)
            }
        })

        viewModel.isAdded.observe(viewLifecycleOwner, Observer {
            getArticles()
        })

        viewModel.isRemoved.observe(viewLifecycleOwner, Observer {
            getArticles()
        })
    }

    private fun setupListeners() {

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
                requireActivity().finish()
            }
        }.show()
    }

    fun onLikeClick(favourite: Boolean, id: Int) {
        if(favourite){
            viewModel.removeFromArticle(id)
        }else{
            viewModel.addToFavArticle(id)
        }
    }

    private fun addArticles(it: List<ArticlesModel>) {
        articlesAdapter.addArticles(it)
    }

    private fun setupRecyclerView() {
        farticlesRv.adapter = articlesAdapter
    }

    private fun getArticles() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.getArticles()
        }
    }

    override fun onResume() {
        super.onResume()
        getArticles()
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteArticlesFragment()
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
}