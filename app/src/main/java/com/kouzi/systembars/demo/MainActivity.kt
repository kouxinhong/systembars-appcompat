package com.kouzi.systembars.demo

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kouzi.systembars.SystemBars
import com.kouzi.systembars.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isShowSystemBar: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val navigationBarsColor = if (mode == Configuration.UI_MODE_NIGHT_YES) {
            if (Build.VERSION.SDK_INT >= 26) R.color.white_300 else R.color.black
        } else {
            if (Build.VERSION.SDK_INT >= 26) R.color.black_300 else R.color.black
        }

        SystemBars.instance.setSystemBars(
            this,
            binding.mainRoot,
            navigationBarsColor,
            binding.bottomNavigation,
            true
        )
        setBadge()

        binding.javaTest.setOnClickListener {
            val i = Intent(this, MainActivity2::class.java)
            startActivity(i)
        }
        binding.navigationBar.setOnClickListener {
            val i = Intent(this, NavigationBarActivity::class.java)
            startActivity(i)
        }

        binding.showSystemBarsBar.setOnClickListener {
            isShowSystemBar = if (isShowSystemBar) {
                SystemBars.instance.hideSystemBars(binding.mainRoot)
                false
            } else {
                SystemBars.instance.showSystemBars(binding.mainRoot)
                true
            }
        }

        binding.showIme.setOnClickListener {
            isShowSystemBar = if (isShowSystemBar) {
                SystemBars.instance.hideIme(binding.mainRoot)
                false
            } else {
                SystemBars.instance.showIme(binding.mainRoot)
                true
            }
        }

    }


    private fun setBadge() {
        val badge = binding.bottomNavigation.getOrCreateBadge(R.id.place)
        badge.isVisible = true
        badge.number = 5
    }
}