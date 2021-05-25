package kz.aknur.newchildcare.content.calendar

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.alert_add_event.*
import kotlinx.android.synthetic.main.alert_add_event.view.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.calendar.adapter.EventsAdapter
import kz.aknur.newchildcare.content.calendar.model.EventsResponse
import org.jetbrains.anko.sdk27.coroutines.onClick


class CalendarFragment : Fragment() {

    private lateinit var viewModel: CalendarViewModel
    private val adapter = EventsAdapter(this)
    private val currentList: ArrayList<EventsResponse.EventsModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lets()
    }

    private fun lets() {
        setupViewModel()
        setupRecyclerView()
        setupObservers()
        setupListeners()
        getEvents()
    }

    private fun setupListeners() {
        addIg.onClick {
            val mDialogView = LayoutInflater.from(requireContext()).inflate(
                R.layout.alert_add_event,
                null
            )
            val mBuilder = AlertDialog.Builder(requireContext())
                .setView(mDialogView)
                .setCancelable(true)
            val mAlertDialog = mBuilder.show()
            mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            mDialogView.event_save.onClick {
                currentList.add(
                    EventsResponse.EventsModel(
                        45,
                        mAlertDialog.event_title.text.toString(),
                        mAlertDialog.event_date.text.toString(),
                        mAlertDialog.event_time.text.toString(),
                        mAlertDialog.event_date_rm.text.toString(),
                        mAlertDialog.event_time_rm.text.toString(),
                        mAlertDialog.event_notes.text.toString()
                    )
                )
                adapter.addArticles(currentList)
                mAlertDialog.dismiss()
            }
        }
    }

    private fun setupObservers() {
        viewModel.events.observe(viewLifecycleOwner, Observer {
            currentList.clear()
            currentList.addAll(it)
            adapter.addArticles(currentList)
        })
    }

    private fun getEvents() {
        viewModel.getArticles()
    }

    private fun setupRecyclerView() {
        id_event_recycler.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CalendarFragment()
    }
}