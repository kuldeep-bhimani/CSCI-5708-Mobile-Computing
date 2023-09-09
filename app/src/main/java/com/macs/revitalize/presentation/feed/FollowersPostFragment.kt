package com.macs.revitalize.presentation.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.macs.revitalize.R

class FollowersPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(com.macs.revitalize.R.layout.fragment_feed, container, false)

        val recyclerView: RecyclerView = view.findViewById(com.macs.revitalize.R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
//        recyclerView.setHasFixedSize(true)
        val data = ArrayList<FeedViewModel>()
        val adapter = CustomAdapter(data)
        recyclerView.adapter = adapter
        val spinner=view.findViewById<Spinner>(com.macs.revitalize.R.id.spinner)
        spinner.visibility= View.INVISIBLE
//        val comments = view.findViewById<TextView>(com.macs.revitalize.R.id.textViewCaption)


        // ArrayList of class FeedViewModel


        // This loop will create 20 Views containing
        // the image with the count of view

//    FeedViewModel.c=4

//        for (i in 1..FeedViewModel.c) {
//            println("inside user")

      data.add(FeedViewModel(R.drawable.ic_social_feed, "Item "))
        data.add(FeedViewModel(R.drawable.habits, "Item "))

        println(data)
//        }
        return view

    }

}