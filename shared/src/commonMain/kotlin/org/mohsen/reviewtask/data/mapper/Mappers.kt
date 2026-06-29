package org.mohsen.reviewtask.data.mapper

import org.mohsen.reviewtask.data.model.ComponentResponseDto
import org.mohsen.reviewtask.domain.model.ComponentType
import org.mohsen.reviewtask.domain.model.UiComponent

fun ComponentResponseDto.toDomain(): UiComponent {
    val componentType = try {
        ComponentType.valueOf(this.data.type.uppercase())
    } catch (e: Exception) {
        ComponentType.UNKNOWN
    }
    
    return UiComponent(
        type = componentType,
        label = this.data.label,
        color = this.data.color,
        fontSize = this.data.fontSize,
        value = this.data.value,
        steps = this.data.steps,
        min = this.data.min,
        max = this.data.max,
        placeholder = this.data.placeholder
    )
}
