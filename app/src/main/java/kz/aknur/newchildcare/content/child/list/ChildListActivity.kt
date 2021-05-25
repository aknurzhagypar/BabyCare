package kz.aknur.newchildcare.content.child.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_child_list.*
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.FoundationActivity
import kz.aknur.newchildcare.content.child.add.ChildAddActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick

class ChildListActivity: AppCompatActivity() {

    private lateinit var viewModel: ChildListViewModel

    private val childAdapter: ChildListAdapter = ChildListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_list)
        lets()
    }

    private fun lets() {
        setupRecyclerView()
        setupViewModel()
        setupListeners()
        setupObservers()
        getChildList()
    }

    private fun setupRecyclerView() {
        child_list_rv.layoutManager = LinearLayoutManager(this)
        child_list_rv.adapter = childAdapter
    }

    private fun getChildList() {
        viewModel.getMyChild()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(ChildListViewModel::class.java)
    }

    private fun setupListeners() {
        add_child_btn.onClick {
            finish()
            startActivity(intentFor<ChildAddActivity>())
        }
        child_list_next_button.onClick {
            finish()
            startActivity(intentFor<FoundationActivity>())
        }
    }

    private fun setupObservers() {
        viewModel.childList.observe(this, Observer {
            childAdapter.addChild(it)
        })
    }


}