package com.example.jetpackdatastoretutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.jetpackdatastoretutorial.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var settingsManager: SettingsManager
    private var isDarkMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsManager = SettingsManager(applicationContext)

        observeUiPreferences()

        binding.imageButton.setOnClickListener {
            setupUi()
        }
    }

    private fun setupUi() {
        lifecycleScope.launch {
            when(isDarkMode) {
                true -> settingsManager.setUiMode(UiMode.LIGHT)
                false -> settingsManager.setUiMode(UiMode.DARK)
            }
        }
    }

    private fun observeUiPreferences() {
        settingsManager.uiModeFlow.asLiveData().observe(this) {
            uiMode ->

            uiMode?.let {
                when(uiMode) {
                    UiMode.LIGHT -> onLightMode()
                    UiMode.DARK -> onDarkMode()
                }
            }
        }
    }
    private fun onLightMode() {
        isDarkMode = false
        binding.layoutMain.setBackgroundColor(
                ContextCompat.getColor(this, android.R.color.white)
        )
        binding.imageButton.setImageResource(R.drawable.ic_moon)
    }


    private fun onDarkMode() {
        isDarkMode = true
        binding.layoutMain.setBackgroundColor(
                ContextCompat.getColor(this, android.R.color.black)
        )
        binding.imageButton.setImageResource(R.drawable.ic_sun)
    }


}