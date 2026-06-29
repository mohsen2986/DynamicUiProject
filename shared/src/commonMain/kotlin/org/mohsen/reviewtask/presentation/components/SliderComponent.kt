package org.mohsen.reviewtask.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import org.mohsen.reviewtask.presentation.theme.AppTheme
import androidx.compose.ui.unit.dp
import org.mohsen.reviewtask.domain.model.ComponentType
import org.mohsen.reviewtask.domain.model.UiComponent

@Composable
fun SliderComponent(
    component: UiComponent,
    onValueChange: (Float) -> Unit // Added callback
) {

    val rangeMin = component.min?.toFloat() ?: 0f
    val rangeMax = component.max?.toFloat() ?: 100f
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        component.text?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = component.color?.parseColor() ?: Color.Unspecified
            )
        }
        Slider(
            value = component.value ?: 0f,
            onValueChange = { newValue ->
                onValueChange(newValue) // Call the callback with the new value
            },
            valueRange = rangeMin..rangeMax,
            steps = component.steps ?: 0,
            colors = SliderDefaults.colors(
                thumbColor = component.color?.parseColor() ?: MaterialTheme.colorScheme.primary,
                activeTrackColor = component.color?.parseColor() ?: MaterialTheme.colorScheme.primary,
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SliderComponentPreviewLight() {
    AppTheme(darkTheme = false) {
        SliderComponent(
            component = UiComponent(
                type = ComponentType.SLIDER,
                text = "Volume Control",
                color = "#6200EA",
                value = 50f,
                min = 0,
                max = 100,
                steps = 10
            ),
            onValueChange = {} // Dummy callback for preview
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SliderComponentPreviewDark() {
    AppTheme(darkTheme = true) {
        SliderComponent(
            component = UiComponent(
                type = ComponentType.SLIDER,
                text = "Volume Control",
                color = "#6200EA",
                value = 50f,
                min = 0,
                max = 100,
                steps = 10
            ),
            onValueChange = {} // Dummy callback for preview
        )
    }
}

