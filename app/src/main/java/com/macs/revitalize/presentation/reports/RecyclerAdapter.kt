package com.macs.revitalize.presentation.reports

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.macs.revitalize.R
import kotlinx.android.synthetic.main.achievement_item.view.*

class RecyclerAdapter(private val dataset:ArrayList<HabitAchievement>): RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(myAchievement: HabitAchievement) {

            view.tittle_View.text = myAchievement.tittle
            view.summary.text = myAchievement.summary
            view.progressText.text = myAchievement.progress.toString()


            if (myAchievement.progress == 0) {
                view.progressBar.setProgress(10)

            }
            if (myAchievement.progress == 100) {
                view.share_Button.visibility = View.VISIBLE
                view.tick_view.visibility = View.VISIBLE
                view.progressText.visibility = View.INVISIBLE
                view.progressBar.progressTintList = ColorStateList.valueOf(Color.parseColor("#48c785"));

            }

            updateProgressBar(view.progressBar, myAchievement.progress)

        }

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.achievement_item, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val achievement = dataset[position]
        holder.bind(achievement)

        holder.itemView.share_Button.setOnClickListener {
            Toast.makeText(it.context, "Sharing Achievement", Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Hey I am glad to share that I have completed my goal Take a look at my profile."
            )

            intent.putExtra(Intent.EXTRA_TITLE, "Share Your Milestone")
            val chooser = Intent.createChooser(intent, "Sharing using....")
            it.context.startActivity(chooser)


        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun updateProgressBar(progressBar: ProgressBar, myProgress: Int) {
        progressBar.setProgress(myProgress)


    }



}