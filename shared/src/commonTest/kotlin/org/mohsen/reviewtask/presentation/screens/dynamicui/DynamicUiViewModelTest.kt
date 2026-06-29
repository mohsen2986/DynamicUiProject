package org.mohsen.reviewtask.presentation.screens.dynamicui

import kotlinx.coroutines.Dispatchers
import org.mohsen.reviewtask.domain.model.ComponentType
import org.mohsen.reviewtask.domain.model.UiComponent
import org.mohsen.reviewtask.domain.repository.UiRepository
import org.mohsen.reviewtask.domain.usecase.GetUiComponentUseCase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DynamicUiViewModelTest {

    @Test
    fun `initial state is Loading`() {
        val fakeRepository = FakeUiRepository()
        val useCase = GetUiComponentUseCase(fakeRepository, Dispatchers.Default)
        val viewModel = DynamicUiViewModel(useCase)

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Loading)
        assertFalse((state as UiState.Loading).isRefresh)
    }

    @Test
    fun `fetchData with success sets Success state`() {
        val expectedComponent = UiComponent(
            type = ComponentType.TEXT,
            text = "Username",
            color = "#0061A4",
            placeholder = "Enter username"
        )
        val fakeRepository = FakeUiRepository().apply {
            setSuccessResult(expectedComponent)
        }
        val useCase = GetUiComponentUseCase(fakeRepository, Dispatchers.Default)
        val viewModel = DynamicUiViewModel(useCase)

        // Wait for init to complete
        Thread.sleep(100)

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Success)
        assertEquals(expectedComponent, (state as UiState.Success).component)
    }

    @Test
    fun `fetchData with error sets Error state`() {
        val fakeRepository = FakeUiRepository().apply {
            setErrorResult("Network error")
        }
        val useCase = GetUiComponentUseCase(fakeRepository, Dispatchers.Default)
        val viewModel = DynamicUiViewModel(useCase)

        // Wait for init to complete
        Thread.sleep(100)

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Error)
        assertEquals("Network error", (state as UiState.Error).message)
    }

    @Test
    fun `fetchData handles error message`() {
        val fakeRepository = FakeUiRepository().apply {
            setErrorResult("Network error")
        }
        val useCase = GetUiComponentUseCase(fakeRepository, Dispatchers.Default)
        val viewModel = DynamicUiViewModel(useCase)

        // Wait for init to complete
        Thread.sleep(100)

        val state = viewModel.uiState.value
        assertTrue(state is UiState.Error)
        assertEquals("Network error", state.message)
    }

    @Test
    fun `fetchData can be called multiple times`() {
        val firstComponent = UiComponent(
            type = ComponentType.TEXT,
            text = "First"
        )
        val secondComponent = UiComponent(
            type = ComponentType.NUMBER,
            text = "Second"
        )
        val fakeRepository = FakeUiRepository()
        val useCase = GetUiComponentUseCase(fakeRepository, Dispatchers.Default)
        val viewModel = DynamicUiViewModel(useCase)

        // First fetch
        fakeRepository.setSuccessResult(firstComponent)
        viewModel.fetchData()
        Thread.sleep(100)

        var state = viewModel.uiState.value
        assertTrue(state is UiState.Success)
        assertEquals("First", (state as UiState.Success).component.text)

        // Second fetch
        fakeRepository.setSuccessResult(secondComponent)
        viewModel.fetchData()
        Thread.sleep(100)

        state = viewModel.uiState.value
        assertTrue(state is UiState.Success)
        assertEquals("Second", (state as UiState.Success).component.text)
    }
}

// Fake repository for testing
class FakeUiRepository : UiRepository {
    private var successResult: UiComponent? = null
    private var errorResult: String? = null

    fun setSuccessResult(component: UiComponent) {
        successResult = component
        errorResult = null
    }

    fun setErrorResult(message: String?) {
        errorResult = message
        successResult = null
    }

    override suspend fun getUiComponent(): Result<UiComponent> {
        return when {
            successResult != null -> Result.success(successResult!!)
            errorResult != null -> Result.failure(Exception(errorResult))
            else -> Result.failure(Exception("No result set"))
        }
    }
}
