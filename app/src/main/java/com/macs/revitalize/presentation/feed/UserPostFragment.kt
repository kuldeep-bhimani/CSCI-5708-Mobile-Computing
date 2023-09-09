package com.macs.revitalize.presentation.feed

import android.opengl.Visibility
import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.macs.revitalize.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserPostFragment : Fragment() {


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
//        val data = ArrayList<FeedViewModel>()
        val adapter = CustomUserPostAdapter(data)
        recyclerView.adapter = adapter
        val spinner=view.findViewById<Spinner>(com.macs.revitalize.R.id.spinner)
        spinner.visibility=View.INVISIBLE


        // ArrayList of class FeedViewModel


        // This loop will create 20 Views containing
        // the image with the count of view

//    FeedViewModel.c=4

//        for (i in 1..FeedViewModel.c) {
//            println("inside user")



        data.add(FeedViewModel(com.macs.revitalize.R.drawable.ic_social_feed, "Item "))
        data.add(FeedViewModel(com.macs.revitalize.R.drawable.ic_social_feed, "Item "))
            println(data)
//        }
        return view

    }

    companion object {
        val data = ArrayList<FeedViewModel>()
    }
}