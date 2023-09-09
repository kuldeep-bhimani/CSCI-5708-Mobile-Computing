package com.macs.revitalize.presentation.reports

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class Adapter(var context: Context, fm: FragmentManager, var totalTabs: Int): FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return totalTabs

    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                Analytics()
            }
            1 -> {
                Achievements()
            }
            else -> getItem(position)
        }

    }

}