package com.macs.revitalize.presentation.reports

import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.macs.revitalize.R
import kotlin.random.Random

/*Analytics class will hold all the data related
 to the user activity and their respective graphs*/
class Analytics : Fragment() {

    lateinit var barChart: BarChart
    lateinit var pieChartX: PieChart

    // on below line we are creating
    // a variable for bar data
    lateinit var barData: BarData

    // on below line we are creating a
    // variable for bar data set
    lateinit var barDataSet: BarDataSet

    // on below line we are creating array list for bar data
    lateinit var barEntriesList: ArrayList<BarEntry>
    val db = Firebase.firestore
    val docRef = db.collection("streakMaintained").document("5EMTIHBctco7AlTIXhDB")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        Inflating the layout to get the analytics fragment in place
        val view = inflater.inflate(R.layout.fragment_analytics2, container, false)
        val list = listOf("Ayush", "Qurram", "Pavithra", "Raghu")
        val randomIndex = Random.nextInt(list.size);
        val randomElement = list[randomIndex]
        val textView: TextView = view.findViewById(R.id.tvNameAnalytics) as TextView
            textView.text = "Last 30 days of $randomElement \uD83D\uDCAA"

        pieChartX = view.findViewById(R.id.pieChart)
        barChart = view.findViewById(R.id.idBarChart)
        pieChartX.setUsePercentValues(true)
//        Starting up with the piechart module as imported in the plugins
        pieChartX.description.isEnabled = false
//        Defining the offset for the piechart activity
        pieChartX.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChartX.dragDecelerationFrictionCoef = 0.95f
        pieChartX.isDrawHoleEnabled = true
        pieChartX.setHoleColor(Color.WHITE)
        pieChartX.setTransparentCircleColor(Color.WHITE)
        pieChartX.setTransparentCircleAlpha(110)
        pieChartX.holeRadius = 58f
        pieChartX.transparentCircleRadius = 61f
        pieChartX.setDrawCenterText(true)

        // on below line we are setting
        // rotation for our pie chart
        pieChartX.rotationAngle = 0f

        // enable rotation of the pieChart by touch
        pieChartX.isRotationEnabled = true
        pieChartX.isHighlightPerTapEnabled = true

        // on below line we are setting animation for our pie chart
        pieChartX.animateY(1400, Easing.EaseInOutQuad)

        // on below line we are disabling our legend for pie chart
        pieChartX.legend.isEnabled = false
        pieChartX.setEntryLabelColor(Color.WHITE)
        pieChartX.setEntryLabelTextSize(12f)
        val entries: ArrayList<PieEntry> = ArrayList()
//       Initialising the firestore configuration for the firebase
          docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    var dataPieChartValue=document.data?.getValue("success")
                    var strSuccess : String = document.data?.getValue("success").toString()
                    var strFaiiled : String = document.data?.getValue("failed").toString()

                    var successPercentage : Float = strSuccess.toFloat();
                    var failedPercentage : Float = strFaiiled.toFloat();
                    entries.add(PieEntry((successPercentage)))
                    entries.add(PieEntry(failedPercentage))

                    // on below line we are setting pie data set
                    val dataSet = PieDataSet(entries, "Mobile OS")

                    // on below line we are setting icons.
                    dataSet.setDrawIcons(false)

                    // on below line we are setting slice for pie
                    dataSet.sliceSpace = 3f
                    dataSet.iconsOffset = MPPointF(0f, 40f)
                    dataSet.selectionShift = 5f

                    // add a lot of colors to list
                    val colors: ArrayList<Int> = ArrayList()
                    colors.add(resources.getColor(R.color.purple_200))
                    colors.add(resources.getColor(R.color.yellow))
                    colors.add(resources.getColor(R.color.red))

                    // on below line we are setting colors.
                    dataSet.colors = colors

                    // on below line we are setting pie data set
                    val data = PieData(dataSet)
                    data.setValueFormatter(PercentFormatter())
                    data.setValueTextSize(15f)
                    data.setValueTypeface(Typeface.DEFAULT_BOLD)
                    data.setValueTextColor(Color.WHITE)
                    pieChartX.data = data

                    // undo all highlights
                    pieChartX.highlightValues(null)

                    // loading chart
                    pieChartX.invalidate()

                    Log.d(TAG, "Fitness data: ${document.data?.getValue("success")}  ")

                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

        // on below line we are calling get bar
        // chart data to add data to our array list
        getBarChartData()

        // on below line we are initializing our bar data set
        barDataSet = BarDataSet(barEntriesList, "Streak Graph")

        // on below line we are initializing our bar data
        barData = BarData(barDataSet)

        // on below line we are setting data to our bar chart
        barChart.data = barData

        // on below line we are setting colors for our bar chart text
        barDataSet.valueTextColor = Color.BLACK

        // on below line we are setting color for our bar data set
        barDataSet.color = resources.getColor(R.color.purple_200)

        // on below line we are setting text size
        barDataSet.valueTextSize = 16f

        // on below line we are enabling description as false
        barChart.description.isEnabled = false
        return view

    }

    private fun getBarChartData() {
        barEntriesList = ArrayList()

        // on below line we are adding data
        // to our bar entries list that will be rendered
        barEntriesList.add(BarEntry(1f, 1f))
        barEntriesList.add(BarEntry(5f, 5f))
        barEntriesList.add(BarEntry(3f, 3f))
        barEntriesList.add(BarEntry(2f, 2f))
        barEntriesList.add(BarEntry(4f, 4f))
    }


}

