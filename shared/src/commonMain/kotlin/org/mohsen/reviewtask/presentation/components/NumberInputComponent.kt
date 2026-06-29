package org.mohsen.reviewtask.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.AndroidUiModes.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import org.mohsen.reviewtask.presentation.theme.AppTheme
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import org.mohsen.reviewtask.domain.model.ComponentType
import org.mohsen.reviewtask.domain.model.UiComponent

@Composable
fun NumberInputComponent(
    component: UiComponent,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = component.value?.toInt()?.toString() ?: "",
        onValueChange = { newValue ->
            Logger.d("newValue: ${newValue}")
            onValueChange(newValue)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        label = {
            component.label?.let {
                Text(
                    text = it,
                    color = component.color?.parseColor() ?: Color.Unspecified
                )
            }
        },
        placeholder = {
            component.placeholder?.let { Text(text = it) }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun NumberInputComponentPreviewLight() {
    AppTheme(darkTheme = false) {
        NumberInputComponent(
            component = UiComponent(
                type = ComponentType.NUMBER,
                text = "Age",
                color = "#00695C",
                placeholder = "Enter your age"
            ),
            onValueChange = {} // Dummy callback for preview
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NumberInputComponentPreviewDark() {
    AppTheme(darkTheme = true) {
        NumberInputComponent(
            component = UiComponent(
                type = ComponentType.NUMBER,
                text = "Age",
                color = "#00695C",
                placeholder = "Enter your age"
            ),
            onValueChange = {} // Dummy callback for preview
        )
    }
}

