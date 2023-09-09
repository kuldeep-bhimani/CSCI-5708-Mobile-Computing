package com.example.calenderappdemo.adapter

import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.calenderappdemo.model.Events
import com.google.android.material.textview.MaterialTextView
import com.macs.revitalize.R
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class MyGridAdapter(
    context: Context,
    var dates: List<Date>,
    var calendarDate: Calendar,
    var eventsList: List<Events>
) : ArrayAdapter<Any?>(context, R.layout.single_cell_layout) {
    var inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val monthDate = dates[position]
        val dateCalender = Calendar.getInstance()
        dateCalender.time = monthDate
        val DayNo = dateCalender[Calendar.DAY_OF_MONTH]
        val diaplyMonth = dateCalender[Calendar.MONTH] + 1
        val displayYear = dateCalender[Calendar.YEAR]
        val currentMonth = calendarDate[Calendar.MONTH] + 1
        val currentYear = calendarDate[Calendar.YEAR]
        var view = convertView
        if (view == null) {
            view = inflater.inflate(R.layout.single_cell_layout, parent, false)
        }

        val calender_day = view!!.findViewById<MaterialTextView>(R.id.calender_day)
        val event_id = view.findViewById<MaterialTextView>(R.id.event_id)
        if (diaplyMonth == currentMonth && displayYear == currentYear) {
            calender_day.setTextColor(context.resources.getColor(R.color.primary))
            event_id.setTextColor(context.resources.getColor(R.color.white))
//            view.setBackgroundColor(context.resources.getColor(R.color.green_unselect))
        } else {
            calender_day.setTextColor(context.resources.getColor(R.color.green_unselect))
//            view.setBackgroundColor(context.resources.getColor(android.R.color.white))
        }
        calender_day.text = DayNo.toString()
        val eventCalender = Calendar.getInstance()
        val arrayList = ArrayList<String>()
        for (i in eventsList.indices) {
            eventCalender.time = convertstringtoDate(eventsList[i].dATE)
            if (DayNo == eventCalender[Calendar.DAY_OF_MONTH] && diaplyMonth == eventCalender[Calendar.MONTH] + 1 && displayYear == eventCalender[Calendar.YEAR]) {
                arrayList.add(eventsList[i].eVENT)
                calender_day.setTextColor(context.resources.getColor(R.color.white))
                view.background = context.resources.getDrawable(R.drawable.black_btn)
                event_id.text = arrayList.size.toString() + " Events"
            } else {
//                view.setBackgroundColor(getContext().getResources().getColor(R.color.green_unselect));
            }
        }
        return view
    }

    fun convertstringtoDate(eventDate: String?): Date? {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        var date: Date? = null
        try {
            date = simpleDateFormat.parse(eventDate)
        } catch (e: Exception) {
            Log.e("TAG", "convertstringtoDate@@@@@@: " + e.message)
        }
        return date
    }

    override fun getItem(position: Int): Any? {
        return dates[position]
    }

    override fun getPosition(item: Any?): Int {
        return dates.indexOf(item)
    }

    override fun getCount(): Int {
        return dates.size
    }
}