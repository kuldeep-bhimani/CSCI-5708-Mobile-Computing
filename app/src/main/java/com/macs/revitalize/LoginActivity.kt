package com.macs.revitalize

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.macs.revitalize.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("INTENT", "In login activity")
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun goToMainActivity(email: String) {
        val switchActivityIntent = Intent(this, MainActivity::class.java)
        switchActivityIntent.putExtra("email", email)
        startActivity(switchActivityIntent)
    }
}