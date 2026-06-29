package org.mohsen.reviewtask.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.mohsen.reviewtask.presentation.screens.dynamicui.DynamicUiScreen
import org.mohsen.reviewtask.presentation.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DynamicUiScreen()
        }
    }
}
