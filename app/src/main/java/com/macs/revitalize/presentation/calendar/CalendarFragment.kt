package com.macs.revitalize.presentation.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.macs.revitalize.MainActivity
import com.macs.revitalize.R


class CalendarFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mainActivity = requireActivity() as MainActivity
        mainActivity.binding.bottomNav.visibility = View.VISIBLE
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

}