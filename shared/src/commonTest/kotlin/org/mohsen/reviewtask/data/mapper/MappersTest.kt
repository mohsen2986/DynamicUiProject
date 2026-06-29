package org.mohsen.reviewtask.data.mapper

import org.mohsen.reviewtask.data.model.ComponentDataDto
import org.mohsen.reviewtask.data.model.ComponentResponseDto
import org.mohsen.reviewtask.domain.model.ComponentType
import org.mohsen.reviewtask.domain.model.UiComponent
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class MappersTest {

    @Test
    fun `toDomain maps text component correctly`() {
        val dto = ComponentResponseDto(
            data = ComponentDataDto(
                type = "text",
                text = "Username",
                color = "#0061A4",
                fontSize = 16,
                placeholder = "Enter username"
            ),
            screenId = "home",
            title = "User Input"
        )

        val result = dto.toDomain()

        assertEquals(ComponentType.TEXT, result.type)
        assertEquals("Username", result.text)
        assertEquals("#0061A4", result.color)
        assertEquals(16, result.fontSize)
        assertEquals("Enter username", result.placeholder)
        assertNull(result.value)
        assertNull(result.steps)
        assertNull(result.min)
        assertNull(result.max)
    }

    @Test
    fun `toDomain maps slider component correctly`() {
        val dto = ComponentResponseDto(
            data = ComponentDataDto(
                type = "slider",
                text = "Volume",
                color = "#6200EA",
                value = 50f,
                steps = 10,
                min = 0,
                max = 100
            ),
            screenId = "home",
            title = "Volume Control"
        )

        val result = dto.toDomain()

        assertEquals(ComponentType.SLIDER, result.type)
        assertEquals("Volume", result.text)
        assertEquals("#6200EA", result.color)
        assertEquals(50f, result.value)
        assertEquals(10, result.steps)
        assertEquals(0, result.min)
        assertEquals(100, result.max)
        assertNull(result.placeholder)
    }

    @Test
    fun `toDomain maps number component correctly`() {
        val dto = ComponentResponseDto(
            data = ComponentDataDto(
                type = "number",
                text = "Age",
                color = "#00695C",
                placeholder = "Enter age"
            ),
            screenId = "home",
            title = "Age Input"
        )

        val result = dto.toDomain()

        assertEquals(ComponentType.NUMBER, result.type)
        assertEquals("Age", result.text)
        assertEquals("#00695C", result.color)
        assertEquals("Enter age", result.placeholder)
        assertNull(result.value)
        assertNull(result.steps)
        assertNull(result.min)
        assertNull(result.max)
    }

    @Test
    fun `toDomain handles unknown component type by defaulting to TEXT`() {
        val dto = ComponentResponseDto(
            data = ComponentDataDto(
                type = "unknown_type",
                text = "Label"
            ),
            screenId = "home"
        )

        val result = dto.toDomain()

        assertEquals(ComponentType.TEXT, result.type)
        assertEquals("Label", result.text)
    }

    @Test
    fun `toDomain handles null values correctly`() {
        val dto = ComponentResponseDto(
            data = ComponentDataDto(
                type = "text"
            ),
            screenId = null,
            title = null
        )

        val result = dto.toDomain()

        assertEquals(ComponentType.TEXT, result.type)
        assertNull(result.text)
        assertNull(result.color)
        assertNull(result.fontSize)
        assertNull(result.placeholder)
        assertNull(result.value)
        assertNull(result.steps)
        assertNull(result.min)
        assertNull(result.max)
    }

    @Test
    fun `toDomain handles case insensitive component type`() {
        val dto = ComponentResponseDto(
            data = ComponentDataDto(
                type = "SLIDER"
            ),
            screenId = "home"
        )

        val result = dto.toDomain()

        assertEquals(ComponentType.SLIDER, result.type)
    }

    @Test
    fun `toDomain handles mixed case component type`() {
        val dto = ComponentResponseDto(
            data = ComponentDataDto(
                type = "NuMbEr"
            ),
            screenId = "home"
        )

        val result = dto.toDomain()

        assertEquals(ComponentType.NUMBER, result.type)
    }

    @Test
    fun `toDomain preserves all optional fields when present`() {
        val dto = ComponentResponseDto(
            data = ComponentDataDto(
                type = "slider",
                text = "Brightness",
                color = "#FF9800",
                fontSize = 14,
                value = 75f,
                steps = 20,
                min = 0,
                max = 100,
                placeholder = "Adjust brightness"
            ),
            screenId = "settings",
            title = "Display Settings"
        )

        val result = dto.toDomain()

        assertEquals(ComponentType.SLIDER, result.type)
        assertEquals("Brightness", result.text)
        assertEquals("#FF9800", result.color)
        assertEquals(14, result.fontSize)
        assertEquals(75f, result.value)
        assertEquals(20, result.steps)
        assertEquals(0, result.min)
        assertEquals(100, result.max)
        assertEquals("Adjust brightness", result.placeholder)
    }
}
