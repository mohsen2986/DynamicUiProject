package org.mohsen.reviewtask.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.mohsen.reviewtask.domain.model.UiComponent

@Composable
fun EmptyBoxComponent(component: UiComponent) {
    val color = component.color?.parseColor() ?: Color.Gray // Default to Gray if color is null or invalid

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(color)
    )
}