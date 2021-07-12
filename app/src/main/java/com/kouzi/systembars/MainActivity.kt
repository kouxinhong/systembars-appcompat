package com.kouzi.systembars

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.core.content.ContextCompat
import com.kouzi.systembars.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        val navigationBarsColor = if( mode == Configuration.UI_MODE_NIGHT_YES){
            if (Build.VERSION.SDK_INT >= 26) R.color.white_300 else R.color.black
        }else{
            if (Build.VERSION.SDK_INT >= 26) R.color.black_300 else R.color.black
        }


        SystemBars.instance.setSystemBars(
            this,
            binding.root,
            R.color.purple_500,
            navigationBarsColor,
            binding.bottomNavigation,
            true
        )
        // SystemBars.instance.showNavigationBars(binding.root,true,)
        setBadge()

        binding.javaTest.setOnClickListener {
            val i = Intent(this, MainActivity2::class.java)
            startActivity(i)
        }
    }



    private fun setBadge() {
        val badge = binding.bottomNavigation.getOrCreateBadge(R.id.place)
        badge.isVisible = true
        badge.number =5
    }
}