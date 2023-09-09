package com.macs.revitalize.presentation.reports

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.macs.revitalize.R
import kotlinx.android.synthetic.main.fragment_reports.*


class ReportsFragment : Fragment() {

     lateinit var tabLayout: TabLayout
     lateinit var viewPager: ViewPager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reports, container, false)

        tabLayout = view.findViewById(R.id.tablayout)
        viewPager = view.findViewById(R.id.viewpager)


        tabLayout.addTab(tabLayout.newTab().setText("Analytics"))
        tabLayout.addTab(tabLayout.newTab().setText("Achievements"))

        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = Adapter(requireActivity(), childFragmentManager, tabLayout.tabCount)
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{

            override fun onTabSelected(tab: TabLayout.Tab?){
                viewPager.currentItem = tab!!.position
            }
            override fun onTabReselected(tab: TabLayout.Tab?){}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })


        return view


    }
}







