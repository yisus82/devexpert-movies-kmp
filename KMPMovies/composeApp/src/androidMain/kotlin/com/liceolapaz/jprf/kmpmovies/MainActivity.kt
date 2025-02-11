package com.liceolapaz.jprf.kmpmovies

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnableTransparentStatusBar()
            App()
        }
    }

    @Composable
    private fun EnableTransparentStatusBar() {
        val view = LocalView.current
        val isDarkMode = isSystemInDarkTheme()
        if (!view.isInEditMode) {
            val window = (view.context as MainActivity).window
            window.statusBarColor = Color.TRANSPARENT
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkMode
        }
    }
}