package org.mohsen.reviewtask.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UiComponent(
    val type: ComponentType,
    val text: String? = null,
    val label: String? = null,
    val color: String? = null,
    val fontSize: Int? = null,
    val value: Float? = null,
    val steps: Int? = null,
    val min: Int? = null,
    val max: Int? = null,
    val placeholder: String? = null,
)

@Serializable
enum class ComponentType {
    @SerialName("text")
    TEXT,
    @SerialName("slider")
    SLIDER,
    @SerialName("number")
    NUMBER,
    @SerialName("empty_box")
    EMPTY_BOX,
    UNKNOWN
}
