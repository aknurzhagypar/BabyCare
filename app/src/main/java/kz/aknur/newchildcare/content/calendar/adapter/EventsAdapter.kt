package kz.aknur.newchildcare.content.calendar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kz.aknur.newchildcare.R
import kz.aknur.newchildcare.content.calendar.CalendarFragment
import kz.aknur.newchildcare.content.calendar.model.EventsResponse

class EventsAdapter(val callback: CalendarFragment) :
    RecyclerView.Adapter<EventsAdapter.EventsHolder>() {

    companion object {
        const val TAG = "EventsAdapter"
    }

    private var eventList: ArrayList<EventsResponse.EventsModel> = ArrayList()

    fun addArticles(eventList: List<EventsResponse.EventsModel>) {
        this.eventList.clear()
        this.eventList.addAll(eventList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventsHolder {
        val root: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventsHolder(root)
    }

    override fun getItemCount(): Int {
        return eventList.count()
    }

    override fun onBindViewHolder(holder: EventsHolder, position: Int) {
        holder.bind(eventList[position], callback)
    }

    class EventsHolder(private val root: View) :
        RecyclerView.ViewHolder(root) {
        private val title: TextView = root.findViewById(R.id.event_title)
        private val date: TextView = root.findViewById(R.id.date)
        private val time: TextView = root.findViewById(R.id.time)
        fun bind(
            article: EventsResponse.EventsModel,
            callback: CalendarFragment
        ) {
            title.text = article.name
            time.text = article.remindAtTime.take(5)
            date.text = article.remindAtDate.take(10)
        }

    }

}