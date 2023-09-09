package com.example.calenderappdemo

import android.R
import android.annotation.TargetApi
import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.calenderappdemo.utils.setStatusBarGradiant
import com.macs.revitalize.databinding.ActivityMain1Binding

class MainActivity : AppCompatActivity() {
    var binding: ActivityMain1Binding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain1Binding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setStatusBarGradiant(this);
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }
}