package com.liceolapaz.jprf.kmpmovies.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Screen(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(
            modifier = modifier.fillMaxSize(),
            content = content
        )
    }
}