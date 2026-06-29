package org.mohsen.reviewtask.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.mohsen.reviewtask.domain.model.ComponentType
import org.mohsen.reviewtask.domain.model.UiComponent

@Composable
fun ComponentRenderer(
    component: UiComponent,
    onComponentValueChange: (UiComponent) -> Unit // Callback from ViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (component.type) {
            ComponentType.TEXT -> TextComponent(
                component = component,
                onValueChange = { newValue ->
                    onComponentValueChange(component.copy(text = newValue))
                }
            )
            ComponentType.SLIDER -> SliderComponent(
                component = component,
                onValueChange = { newValue ->
                    onComponentValueChange(component.copy(value = newValue))
                }
            )
            ComponentType.NUMBER -> NumberInputComponent(
                component = component,
                onValueChange = { newValue ->
                    onComponentValueChange(component.copy(value = newValue.toFloat()))
                }
            )
            ComponentType.EMPTY_BOX -> EmptyBoxComponent(component = component)
            ComponentType.UNKNOWN -> UnknownComponent()
        }
    }
}

@Composable
fun UnknownComponent() {
    Text(
        text = "Unknown Component Type",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.error
    )
}
