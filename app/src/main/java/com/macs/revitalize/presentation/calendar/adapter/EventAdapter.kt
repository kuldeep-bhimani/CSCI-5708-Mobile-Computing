package com.example.calenderappdemo.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.calenderappdemo.adapter.EventAdapter.MyHolder
import com.example.calenderappdemo.db.Dbhelper
import android.view.ViewGroup
import android.view.LayoutInflater
import android.database.sqlite.SQLiteDatabase
import com.example.calenderappdemo.db.DbStructure
import android.content.Intent
import com.example.calenderappdemo.service.AlaramReceiver
import android.app.PendingIntent
import android.app.AlarmManager
import android.content.Context
import android.util.Log
import android.view.View
import com.example.calenderappdemo.model.Events
import com.macs.revitalize.databinding.ShowEventRawLayoutBinding
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(var context: Context, arrayList1: ArrayList<Events>) :
    RecyclerView.Adapter<MyHolder>() {
    var arrayList = ArrayList<Events>()
    var dbhelper: Dbhelper? = null

    init {
        arrayList = arrayList1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = ShowEventRawLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MyHolder(binding)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val events = arrayList[position]
        holder.binding.tvEventdate.text = events.dATE
        holder.binding.tvEventname.text = events.eVENT
        holder.binding.tvEventtime.text = events.tIME
        holder.binding.btnDelete.setOnClickListener {
            delete_calenderevent(events.eVENT, events.dATE, events.tIME)
            arrayList.removeAt(position)
            notifyDataSetChanged()
        }
        if (isAlarm(events.dATE, events.eVENT, events.tIME)) {
            holder.binding.imgNotify.visibility = View.VISIBLE
//                        notifyDataSetChanged();
        } else {
            holder.binding.imgNotify.visibility = View.GONE
            //            notifyDataSetChanged();
        }
        val datecalender = Calendar.getInstance()
        datecalender.time = convertstringtoDate(events.dATE)
        val alaramYear = datecalender[Calendar.YEAR]
        val alaramMonth = datecalender[Calendar.MONTH]
        val alaramDay = datecalender[Calendar.DAY_OF_MONTH]
        val timecalender = Calendar.getInstance()
        timecalender.time = convertstringtoTime(events.tIME)
        val alaramHours = timecalender[Calendar.HOUR_OF_DAY]
        val alaramMintes = timecalender[Calendar.MINUTE]
        holder.binding.imgNotify.setOnClickListener {
            if (isAlarm(events.dATE, events.eVENT, events.tIME)) {
                holder.binding.imgNotify.visibility = View.GONE
                CancelAlarm(generateRequestCode(events.dATE, events.eVENT, events.tIME))
                updateEvent(events.dATE, events.eVENT, events.tIME, "off")
                notifyDataSetChanged()
            } else {
                holder.binding.imgNotify.visibility = View.VISIBLE
                val alaramcalender = Calendar.getInstance()
                alaramcalender[alaramYear, alaramMonth, alaramDay, alaramHours] = alaramMintes
                setAlarm(
                    alaramcalender,
                    events.eVENT,
                    events.tIME,
                    generateRequestCode(events.dATE, events.eVENT, events.tIME)
                )
                updateEvent(events.dATE, events.eVENT, events.tIME, "on")
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class MyHolder(var binding: ShowEventRawLayoutBinding) : RecyclerView.ViewHolder(
        binding.root
    )

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

    fun convertstringtoTime(eventDate: String?): Date? {
        val simpleDateFormat = SimpleDateFormat("kk:mm", Locale.ENGLISH)
        var date: Date? = null
        try {
            date = simpleDateFormat.parse(eventDate)
        } catch (e: Exception) {
            Log.e("TAG", "convertstringtoDate@@@@@@: " + e.message)
        }
        return date
    }

    fun delete_calenderevent(event: String?, date: String?, time: String?) {
        dbhelper = Dbhelper(context)
        val database = dbhelper!!.writableDatabase
        dbhelper!!.Delete_Event(event!!, date!!, time!!, database)
        dbhelper!!.close()
    }

    fun isAlarm(date: String?, event: String?, time: String?): Boolean {
        var alarm = false
        dbhelper = Dbhelper(context)
        val database = dbhelper!!.readableDatabase
        val cursor = dbhelper!!.Read_IDEvent(date!!, event!!, time!!, database)
        while (cursor.moveToNext()) {
            val notify = cursor.getString(cursor.getColumnIndex(DbStructure.Notify))
            alarm = if (notify == "on") {
                true
            } else {
                false
            }
        }
        cursor.close()
        dbhelper!!.close()
        return alarm
    }

    fun setAlarm(calendar: Calendar, event: String?, time: String?, requestCode: Int) {
        val intent = Intent(context, AlaramReceiver::class.java)
        intent.putExtra("event", event)
        intent.putExtra("time", time)
        intent.putExtra("id", requestCode)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, calendar.timeInMillis] = pendingIntent
    }

    fun CancelAlarm(requestCode: Int) {
        val intent = Intent(context, AlaramReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }

    fun generateRequestCode(date: String?, event: String?, time: String?): Int {
        var code = 0
        dbhelper = Dbhelper(context)
        val database = dbhelper!!.readableDatabase
        val cursor = dbhelper!!.Read_IDEvent(date!!, event!!, time!!, database)
        while (cursor.moveToNext()) {
            code = cursor.getInt(cursor.getColumnIndex(DbStructure.ID))
        }
        cursor.close()
        dbhelper!!.close()
        return code
    }

    fun updateEvent(date: String?, event: String?, time: String?, notitfy: String?) {
        dbhelper = Dbhelper(context)
        val database = dbhelper!!.writableDatabase
        dbhelper!!.Update_Event(date!!, event!!, time!!, notitfy, database)
        dbhelper!!.close()
    }
}