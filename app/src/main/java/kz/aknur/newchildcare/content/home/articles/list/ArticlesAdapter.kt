package kz.aknur.newchildcare.content.home.articles.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kz.aknur.newchildcare.R
import kotlin.collections.ArrayList

class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ArticlesHolder> {

    companion object {
        const val TAG = "ArticlesAdapter"
    }

    private var articleList: ArrayList<ArticlesModel> = ArrayList()
    private var callback: ArticleListActivity

    constructor(callback: ArticleListActivity) : super() {
        this.callback = callback
    }

    fun addArticles(articleList: List<ArticlesModel>) {
        this.articleList.clear()
        this.articleList.addAll(articleList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticlesHolder {
        val root: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_acticle, parent, false)
        return ArticlesHolder(root)
    }

    override fun getItemCount(): Int {
        return articleList.count()
    }

    override fun onBindViewHolder(holder: ArticlesHolder, position: Int) {
        holder.bind(articleList.get(position), callback)
    }

    class ArticlesHolder(private val root: View) :
        RecyclerView.ViewHolder(root) {
        private val image: ImageView = root.findViewById(R.id.article_image)
        private val topic: TextView = root.findViewById(R.id.article_topic)
        private val favorite: ImageView = root.findViewById(R.id.article_like)
        fun bind(
            article: ArticlesModel,
            callback: ArticleListActivity
        ) {
            if (article.isFavourite){
                favorite.setImageResource(R.drawable.ic_baseline_favorite_24)
            }else{
                favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            topic.text = article.topic

            Glide.with(root)
                .load(article.icon)
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(image)
            image.visibility = View.VISIBLE
            root.setOnClickListener { v: View? ->
                callback.onArticleClick(
                    article
                )
            }
        }

    }

}

