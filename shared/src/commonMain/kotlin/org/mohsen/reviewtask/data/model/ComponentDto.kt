package org.mohsen.reviewtask.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ComponentResponseDto(
    @SerialName("component")
    val data: ComponentDataDto,
    val screenId: String? = null,
    val title: String? = null
)

@Serializable
data class ComponentDataDto(
    val type: String,
    val label: String? = null,
    val color: String? = null,
    val fontSize: Int? = null,
    val value: Float? = null,
    val steps: Int? = null,
    val id: String? = null,
    val min: Int? = null,
    val max: Int? = null,
    val placeholder: String? = null
)
