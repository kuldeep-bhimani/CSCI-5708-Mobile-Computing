package com.example.calenderappdemo

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.content.Intent
import android.os.Handler
import com.example.calenderappdemo.MainActivity
import com.macs.revitalize.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    var binding: ActivitySplashBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        utils.setStatusBarGradiant(this);
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 1000)
    }

    override fun onBackPressed() {}
}