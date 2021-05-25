package kz.aknur.newchildcare.content.child.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.aknur.newchildcare.R
import kotlin.collections.ArrayList

class ChildListAdapter : RecyclerView.Adapter<ChildListAdapter.ChildHolder> {

    companion object {
        const val TAG = "ChildListAdapter"
    }

    private var childList: ArrayList<ChildListResponse.ChildModel> = ArrayList()
    private var callback: ChildListActivity

    constructor(callback: ChildListActivity) : super() {
        this.callback = callback
    }

    fun addChild(childList: List<ChildListResponse.ChildModel>) {
        this.childList.clear()
        this.childList.addAll(childList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChildHolder {
        val root: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_child, parent, false)
        return ChildHolder(root)
    }

    override fun getItemCount(): Int {
        return childList.count()
    }

    override fun onBindViewHolder(holder: ChildHolder, position: Int) {
        holder.bind(childList.get(position), callback)
    }

    class ChildHolder(private val root: View) :
        RecyclerView.ViewHolder(root) {
        private val name: TextView = root.findViewById(R.id.child_name)
        private val age: TextView = root.findViewById(R.id.child_age)
        fun bind(
            children: ChildListResponse.ChildModel,
            callback: ChildListActivity
        ) {

            name.text = children.nickname
            age.text = children.birthDate


            root.setOnClickListener { v: View? ->
//                callback.onArticleClick(
//                    article
//                )
            }
        }

    }

}

