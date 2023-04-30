package com.example.mvvm_by_umar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvm_by_umar.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /**on button Click new Mian Activity Open**/
        binding.btnStart.setOnClickListener {
            var intent=Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
        }

    }
}