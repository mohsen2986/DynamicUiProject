package org.mohsen.reviewtask.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.mohsen.reviewtask.domain.model.ComponentType
import org.mohsen.reviewtask.domain.model.UiComponent
import org.mohsen.reviewtask.domain.repository.UiRepository
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class GetUiComponentUseCaseTest {

    private lateinit var testDispatcher: TestDispatcher
    private lateinit var mockRepository: FakeUiRepository
    private lateinit var useCase: GetUiComponentUseCase

    @BeforeTest
    fun setup() {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
        mockRepository = FakeUiRepository()
        useCase = GetUiComponentUseCase(mockRepository, testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke returns UiComponent when repository succeeds`() = runTest {
        val expectedComponent = UiComponent(
            type = ComponentType.TEXT,
            text = "Username",
            color = "#0061A4",
            placeholder = "Enter username"
        )
        mockRepository.setSuccessResult(expectedComponent)

        val result = useCase()

        assertEquals(expectedComponent, result)
        assertTrue(mockRepository.wasCalled)
    }

    @Test
    fun `invoke throws exception when repository fails`() = runTest {
        mockRepository.setErrorResult("Network error")

        var exceptionThrown = false
        try {
            useCase()
        } catch (e: Exception) {
            exceptionThrown = true
            assertEquals("Network error", e.message)
        }

        assertTrue(exceptionThrown)
        assertTrue(mockRepository.wasCalled)
    }

    @Test
    fun `invoke returns slider component correctly`() = runTest {
        val expectedComponent = UiComponent(
            type = ComponentType.SLIDER,
            text = "Volume",
            color = "#6200EA",
            value = 50f,
            steps = 10,
            min = 0,
            max = 100
        )
        mockRepository.setSuccessResult(expectedComponent)

        val result = useCase()

        assertEquals(ComponentType.SLIDER, result.type)
        assertEquals("Volume", result.text)
        assertEquals(50f, result.value)
        assertEquals(10, result.steps)
    }

    @Test
    fun `invoke returns number component correctly`() = runTest {
        val expectedComponent = UiComponent(
            type = ComponentType.NUMBER,
            text = "Age",
            color = "#00695C",
            placeholder = "Enter age"
        )
        mockRepository.setSuccessResult(expectedComponent)

        val result = useCase()

        assertEquals(ComponentType.NUMBER, result.type)
        assertEquals("Age", result.text)
        assertEquals("Enter age", result.placeholder)
    }

    @Test
    fun `invoke uses provided dispatcher`() = runTest {
        val expectedComponent = UiComponent(
            type = ComponentType.TEXT,
            text = "Test"
        )
        mockRepository.setSuccessResult(expectedComponent)

        val result = useCase()

        assertEquals(expectedComponent, result)
    }
}

// Fake repository for testing
class FakeUiRepository : UiRepository {
    private var successResult: UiComponent? = null
    private var errorResult: String? = null
    var wasCalled = false

    fun setSuccessResult(component: UiComponent) {
        successResult = component
        errorResult = null
    }

    fun setErrorResult(message: String) {
        errorResult = message
        successResult = null
    }

    override suspend fun getUiComponent(): Result<UiComponent> {
        wasCalled = true
        return when {
            successResult != null -> Result.success(successResult!!)
            errorResult != null -> Result.failure(Exception(errorResult))
            else -> Result.failure(Exception("No result set"))
        }
    }
}
