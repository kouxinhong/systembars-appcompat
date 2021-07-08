package com.kouzi.systembars

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kouzi.systembars.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigationBarsColor= if(Build.VERSION.SDK_INT >= 26) R.color.white_300 else R.color.black

        SystemBars.instance.setSystemBars(
            this,
            binding.root,
            R.color.purple_500,
            navigationBarsColor,
            binding.bottomNavigation,
            false
        )
       // SystemBars.instance.showNavigationBars(binding.root,true,)
        setBadge()

        binding.javaTest.setOnClickListener {
            val i = Intent(this,MainActivity2::class.java)
            startActivity(i)
        }
    }

    private fun setBadge() {
        val badge = binding.bottomNavigation.getOrCreateBadge(R.id.place)
        badge.isVisible = true
        badge.number = 5
    }
}