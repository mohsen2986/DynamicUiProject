package org.mohsen.reviewtask.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
fun TextComponent(
    component: UiComponent,
    onValueChange: (String) -> Unit
) {

    OutlinedTextField(
        value = component.text ?: "",
        onValueChange = { newValue ->
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
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun TextComponentPreviewLight() {
    AppTheme(darkTheme = false) {
        TextComponent(
            component = UiComponent(
                type = ComponentType.TEXT,
                text = "Username",
                color = "#0061A4",
                placeholder = "Enter your username"
            ),
            onValueChange = {} // Dummy callback for preview
        )
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun TextComponentPreviewDark() {
    AppTheme(darkTheme = true) {
        TextComponent(
            component = UiComponent(
                type = ComponentType.TEXT,
                text = "Username",
                color = "#0061A4",
                placeholder = "Enter your username"
            ),
            onValueChange = {} // Dummy callback for preview
        )
    }
}
