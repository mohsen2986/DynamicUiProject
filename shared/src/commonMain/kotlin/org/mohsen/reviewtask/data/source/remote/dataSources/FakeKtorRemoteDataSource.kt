package org.mohsen.reviewtask.data.source.remote.dataSources

import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import org.mohsen.reviewtask.data.model.ComponentResponseDto
import org.mohsen.reviewtask.data.source.remote.base.HTTPException
import org.mohsen.reviewtask.data.source.remote.base.TypedResult

class FakeKtorRemoteDataSource : RemoteDataSource {
    private val textUserInputResponse = """
        {
          "screenId": "home",
          "title": "User Input",
          "component": {
            "type": "text",
            "id": "user_prompt",
            "label": "Ask something!",
            "placeholder": "Type..."
          }
        }
    """.trimIndent()

    private val numberUserInputResponse = """
       {
         "screenId": "home",
         "title": "Age Input",
         "component": {
           "type": "number",
           "id": "age",
           "label": "Age",
           "placeholder": "Enter age"
         }
       }
    """.trimIndent()

    private val sliderInputResponse = """
        {
          "screenId": "home",
          "title": "Volume",
          "component": {
            "type": "slider",
            "id": "volume",
            "label": "Volume",
            "min": 0,
            "max": 100,
            "value": 50
          }
        }
    """.trimIndent()

    private val emptyBoxResponse = """
        {
          "screenId": "home",
          "title": "Colored Box",
          "component": {
            "type": "empty_box",
            "color": "#FF0000"
          }
        }
    """.trimIndent()

    private val failedResponse = """
        {
          "screenId": "home",
          "title": "Volume",
          "component": {
          
          }
        }
    """.trimIndent()

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    override suspend fun getUiComponent(): TypedResult<ComponentResponseDto, HTTPException> {
        return try {
            delay(2_000)
            // For demonstration, returning the emptyBoxResponse. 
            // In a real scenario, this would be dynamic or configurable.
            val result = json.decodeFromString<ComponentResponseDto>(textUserInputResponse)
            TypedResult.Success(result, 200)
        } catch (e: Exception) {
            println("Error fetching UI component: $e")
            TypedResult.Error(
                HTTPException(
                    code = 500,
                    message = "Failed to fetch UI component: ${e.message}"
                )
            )
        }
    }
}
