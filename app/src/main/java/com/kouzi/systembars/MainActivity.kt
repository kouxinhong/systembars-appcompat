package com.kouzi.systembars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kouzi.systembars.databinding.ActivityMainBinding
import com.kouzi.systembars.R
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SystemBars.instance.setSystemBars(this,binding.root,R.color.purple_500,binding.bottomNavigation)
        val badge = binding.bottomNavigation.getOrCreateBadge(R.id.place)
        badge.isVisible = true
        badge.number =5
    }
}