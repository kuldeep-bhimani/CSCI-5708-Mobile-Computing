package com.example.calenderappdemo

import android.R
import android.app.Application
import android.view.WindowManager

import android.graphics.drawable.Drawable

import android.os.Build

import android.app.Activity

import android.annotation.TargetApi
import android.view.Window


class AppClass : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    fun setStatusBarGradiant(activity: Activity, splash_bg: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity.window
            val background = activity.resources.getDrawable(splash_bg)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setStatusBarColor(activity.resources.getColor(R.color.transparent))
            window.setNavigationBarColor(activity.resources.getColor(R.color.transparent))
            window.setBackgroundDrawable(background)
        }
    }

}