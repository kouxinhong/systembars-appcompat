package com.kouzi.systembars.demo

import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kouzi.systembars.SystemBars
import com.kouzi.systembars.demo.databinding.ActivityNavigationBarBinding

class NavigationBarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNavigationBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        val navigationBarsColor = if (mode == Configuration.UI_MODE_NIGHT_YES) {
            if (Build.VERSION.SDK_INT >= 26) R.color.white_300 else R.color.black
        } else {
            if (Build.VERSION.SDK_INT >= 26) R.color.black_300 else R.color.black
        }

        SystemBars.instance.setNavigationBar(
            this,
            binding.navigationBarRoot,
            navigationBarsColor,
            binding.bottomNavigation,
            true
        )
    }
}