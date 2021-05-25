package kz.aknur.newchildcare.content.home.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.home.HomeFragment
import kz.aknur.newchildcare.content.home.categories.models.LargeCategoriesModel
import kotlin.collections.ArrayList

class LargeCategoriesAdapter : RecyclerView.Adapter<LargeCategoriesAdapter.LargeCategoriesHolder> {

    companion object {
        const val TAG = "LargeCategoriesAdapter"
    }

    private var largeCategoriesList: ArrayList<LargeCategoriesModel> = ArrayList()
    private var callback: HomeFragment

    constructor(callback: HomeFragment) : super() {
        this.callback = callback
    }

    fun addLargeCat(largeCategoriesList: List<LargeCategoriesModel>) {
        this.largeCategoriesList.addAll(largeCategoriesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LargeCategoriesHolder {
        val root: View =
            LayoutInflater.from(parent.context).inflate(R.layout.large_category_item, parent, false)
        return LargeCategoriesHolder(root)
    }

    override fun getItemCount(): Int {
        return largeCategoriesList.count()
    }

    override fun onBindViewHolder(holder: LargeCategoriesHolder, position: Int) {
        holder.bind(largeCategoriesList.get(position), callback)
    }

    class LargeCategoriesHolder(private val root: View) :
        RecyclerView.ViewHolder(root) {
        private val largeCatImage: ImageView = root.findViewById(R.id.largeCatImage)
        private val largeCatTitle: TextView = root.findViewById(R.id.largeCatTitle)
        fun bind(
            largeCategory: LargeCategoriesModel,
            callback: HomeFragment
        ) {
            largeCatTitle.text = largeCategory.name

//            Glide.with(root)
//                 .load(largeCategory.icon)
//                 .placeholder(R.drawable.placeholder)
//                 .diskCacheStrategy(DiskCacheStrategy.NONE)
//                 .skipMemoryCache(true)
//                 .into(largeCatImage)
            largeCatImage.visibility = View.VISIBLE

            root.setOnClickListener { v: View? ->
                callback.onLargeCategoryClick(
                    largeCategory
                )
            }
        }

    }

}

