package com.example.calenderappdemo.view

import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.example.calenderappdemo.db.Dbhelper
import com.example.calenderappdemo.adapter.MyGridAdapter
import android.widget.AdapterView.OnItemClickListener
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatEditText
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.widget.AdapterView.OnItemLongClickListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.calenderappdemo.adapter.EventAdapter
import android.database.sqlite.SQLiteDatabase
import android.content.Intent
import com.example.calenderappdemo.service.AlaramReceiver
import android.app.PendingIntent
import android.app.AlarmManager
import android.content.Context
import android.util.AttributeSet
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.calenderappdemo.view.CustomiseCalenderView
import com.example.calenderappdemo.db.DbStructure
import com.example.calenderappdemo.model.Events
import com.macs.revitalize.R
import java.text.SimpleDateFormat
import java.util.*

class CustomiseCalenderView : LinearLayout {
    var imgbtn_preview: ShapeableImageView? = null
    var imgbtn_forword: ShapeableImageView? = null
    var gridview: GridView? = null
    var tv_currentdate: MaterialTextView? = null
    var calendar = Calendar.getInstance(Locale.ENGLISH)
    var dateFormat = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    var monthFormat = SimpleDateFormat("MMMM", Locale.ENGLISH)
    var yearFormat = SimpleDateFormat("yyyy", Locale.ENGLISH)
    var eventDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    var dates: MutableList<Date> = ArrayList()
    var eventsList: MutableList<Events> = ArrayList()
    var alertDialog: AlertDialog? = null
    var dbhelper: Dbhelper? = null
    var myGridAdapter: MyGridAdapter? = null
    var alarmYear = 0
    var alarmMonth = 0
    var alarmDay = 0
    var alarmHour = 0
    var alarmMinit = 0
    var alarmMe = 0

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
//        this.context = getContext()
        init_layout()
        setupcalender()
        imgbtn_preview!!.setOnClickListener {
            calendar.add(Calendar.MONTH, -1)
            setupcalender()
        }
        imgbtn_forword!!.setOnClickListener {
            calendar.add(Calendar.MONTH, 1)
            setupcalender()
        }
        gridview!!.onItemLongClickListener = OnItemLongClickListener() { adapterView, view, i, l ->
            val builder = AlertDialog.Builder(
                context!!
            )
            builder.setCancelable(true)
            val addview =
                LayoutInflater.from(adapterView.context).inflate(R.layout.add_newevent_layout, null)
            val et_addtypevent = addview.findViewById<AppCompatEditText>(R.id.et_addtypevent)
            val img_settimeevent = addview.findViewById<ShapeableImageView>(R.id.img_settimeevent)
            val tv_eventtime = addview.findViewById<MaterialTextView>(R.id.tv_eventtime)
            val btn_addevent = addview.findViewById<Button>(R.id.btn_addevent)
            val chk_alarmme = addview.findViewById<CheckBox>(R.id.chk_alarmme)
            val dateCalender = Calendar.getInstance()
            dateCalender.time = dates[i]
            alarmYear = dateCalender[Calendar.YEAR]
            alarmMonth = dateCalender[Calendar.MONTH]
            alarmDay = dateCalender[Calendar.DAY_OF_MONTH]
            img_settimeevent.setOnClickListener { view ->
                val calendar = Calendar.getInstance()
                val hours = calendar[Calendar.HOUR_OF_DAY]
                val minuts = calendar[Calendar.MINUTE]
                val timePickerDialog =
                    TimePickerDialog(view.context, { timePicker, hoursofDay, minutune ->
                        val c = Calendar.getInstance()
                        c[Calendar.HOUR_OF_DAY] = hoursofDay
                        c[Calendar.MINUTE] = minutune
                        c.timeZone = TimeZone.getDefault()
                        val hformat = SimpleDateFormat("K:mm a", Locale.ENGLISH)
                        val event_time = hformat.format(c.time)
                        tv_eventtime.text = event_time
                        alarmHour = c[Calendar.HOUR_OF_DAY]
                        alarmMinit = c[Calendar.MINUTE]
                    }, hours, minuts, false)
                timePickerDialog.show()
            }
            val date = eventDateFormat.format(dates[i])
            val month = monthFormat.format(dates[i])
            val year = yearFormat.format(dates[i])
            btn_addevent.setOnClickListener {
                if (chk_alarmme.isChecked) {
                    save_event(
                        et_addtypevent.text.toString().trim { it <= ' ' },
                        tv_eventtime.text.toString().trim { it <= ' ' },
                        date,
                        month,
                        year,
                        "on"
                    )
                    val calendar = Calendar.getInstance()
                    calendar[alarmYear, alarmMonth, alarmDay, alarmHour] = alarmMinit
                    setAlarm(
                        calendar,
                        et_addtypevent.text.toString(),
                        tv_eventtime.text.toString(),
                        generateRequestCode(
                            date,
                            et_addtypevent.text.toString().trim { it <= ' ' },
                            tv_eventtime.text.toString().trim { it <= ' ' })
                    )
                    setupcalender()
                    alertDialog!!.dismiss()
                } else {
                    save_event(
                        et_addtypevent.text.toString().trim { it <= ' ' },
                        tv_eventtime.text.toString().trim { it <= ' ' },
                        date,
                        month,
                        year,
                        "off"
                    )
                    setupcalender()
                    alertDialog!!.dismiss()
                }
            }
            builder.setView(addview)
            alertDialog = builder.create()
            alertDialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            alertDialog!!.show()
            true
        }
        gridview!!.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
            val date = eventDateFormat.format(dates[i])

            val rcy_event = findViewById<RecyclerView>(R.id.rcy_event)
            rcy_event.setHasFixedSize(true)
            rcy_event.layoutManager = LinearLayoutManager(context)
            val eventAdapter = EventAdapter(context!!, CollectionEventByDate(date))
            rcy_event.adapter = eventAdapter
        }
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    fun init_layout() {
        val layoutInflater =
            context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.calender_view, this)
        imgbtn_preview = view.findViewById(R.id.imgbtn_preview)
        imgbtn_forword = view.findViewById(R.id.imgbtn_forword)
        tv_currentdate = view.findViewById(R.id.tv_currentdate)
        gridview = view.findViewById(R.id.gridview)
    }

    fun save_event(
        event: String?,
        time: String?,
        date: String?,
        month: String?,
        year: String?,
        notify: String?
    ) {
        dbhelper = Dbhelper(context)
        val sqLiteDatabase = dbhelper!!.writableDatabase
        dbhelper!!.Save_Event(event, time, date, month, year, notify, sqLiteDatabase)
        dbhelper!!.close()
        Toast.makeText(context, "Event Saved!!!", Toast.LENGTH_SHORT).show()
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
        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, calendar.timeInMillis] = pendingIntent
    }

    fun setupcalender() {
        val currentdate = dateFormat.format(calendar.time)
        tv_currentdate!!.text = currentdate
        dates.clear()
        val monthcalender = calendar.clone() as Calendar
        monthcalender[Calendar.DAY_OF_MONTH] = 1
        val firstdayofmonth = monthcalender[Calendar.DAY_OF_WEEK] - 1
        monthcalender.add(Calendar.DAY_OF_MONTH, -firstdayofmonth)
        CollectEventPerMonth(monthFormat.format(calendar.time), yearFormat.format(calendar.time))
        while (dates.size < MAX_CALENDER_DAY) {
            dates.add(monthcalender.time)
            monthcalender.add(Calendar.DAY_OF_MONTH, 1)
        }
        myGridAdapter = MyGridAdapter(context!!, dates, calendar, eventsList)
        gridview!!.adapter = myGridAdapter
    }

    fun CollectEventPerMonth(month1: String?, year1: String?) {
        eventsList.clear()
        dbhelper = Dbhelper(context)
        val sqLiteDatabase = dbhelper!!.readableDatabase
        val cursor = dbhelper!!.Read_Event_Supermonth(month1!!, year1!!, sqLiteDatabase)
        while (cursor.moveToNext()) {
            val event = cursor.getString(cursor.getColumnIndex(DbStructure.EVENT))
            val time = cursor.getString(cursor.getColumnIndex(DbStructure.TIME))
            val date = cursor.getString(cursor.getColumnIndex(DbStructure.DATE))
            val month = cursor.getString(cursor.getColumnIndex(DbStructure.MONTH))
            val year = cursor.getString(cursor.getColumnIndex(DbStructure.YEAR))
            val events = Events(event, time, date, month, year)
            eventsList.add(events)
        }
        cursor.close()
        dbhelper!!.close()
    }

    fun CollectionEventByDate(date1: String?): ArrayList<Events> {
        val arrayList = ArrayList<Events>()
        dbhelper = Dbhelper(context)
        val sqLiteDatabase = dbhelper!!.readableDatabase
        val cursor = dbhelper!!.Read_Event(date1!!, sqLiteDatabase)
        while (cursor.moveToNext()) {
            val event = cursor.getString(cursor.getColumnIndex(DbStructure.EVENT))
            val time = cursor.getString(cursor.getColumnIndex(DbStructure.TIME))
            val date = cursor.getString(cursor.getColumnIndex(DbStructure.DATE))
            val month = cursor.getString(cursor.getColumnIndex(DbStructure.MONTH))
            val year = cursor.getString(cursor.getColumnIndex(DbStructure.YEAR))
            val events = Events(event, time, date, month, year)
            arrayList.add(events)
        }
        cursor.close()
        dbhelper!!.close()
        return arrayList
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

    companion object {
        var MAX_CALENDER_DAY = 42
    }
}