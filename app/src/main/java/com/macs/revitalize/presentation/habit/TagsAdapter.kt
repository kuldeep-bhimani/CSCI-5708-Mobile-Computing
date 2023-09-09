package com.macs.revitalize.presentation.habit

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.macs.revitalize.R

class TagsAdapter(val tags: MutableList<String>): RecyclerView.Adapter<TagsAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class ViewHolder(itemView: View, listener: onItemClickListener): RecyclerView.ViewHolder(itemView) {
        val tagText: TextView = itemView.findViewById(R.id.tagItemText)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(position = adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tagText.text = tags[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.tag_item, parent, false)
        return ViewHolder(view, mListener)
    }

    fun addNewItem(newTags: MutableList<String>){
        tags.clear()
        tags.addAll(newTags)
        notifyDataSetChanged()
    }


}