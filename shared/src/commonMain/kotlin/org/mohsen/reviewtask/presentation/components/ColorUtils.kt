package org.mohsen.reviewtask.presentation.components

import androidx.compose.ui.graphics.Color

fun String.parseColor(): Color? {
    val hex = trim().removePrefix("#")

    return try {
        val value = when (hex.length) {
            6 -> {
                // RRGGBB
                val rgb = hex.toLong(16)
                0xFF000000 or rgb
            }
            8 -> {
                // AARRGGBB
                hex.toLong(16)
            }
            else -> return null
        }

        Color(value)
    } catch (e: Exception) {
        null
    }
}
