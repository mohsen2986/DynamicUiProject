package org.mohsen.reviewtask.presentation.screens.dynamicui

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.*
import org.mohsen.reviewtask.domain.model.ComponentType
import org.mohsen.reviewtask.domain.model.UiComponent
import org.mohsen.reviewtask.domain.repository.UiRepository
import org.mohsen.reviewtask.domain.usecase.GetUiComponentUseCase
import kotlin.test.*

class DynamicUiViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        val fakeRepository = FakeUiRepository()
        val useCase = GetUiComponentUseCase(fakeRepository, testDispatcher)
        val viewModel = DynamicUiViewModel(useCase)

        val state = viewModel.uiState.value

        assertTrue(state is UiState.Loading)
        assertFalse((state as UiState.Loading).isRefresh)
    }

    @Test
    fun `fetchData success`() = runTest {
        val expected = UiComponent(
            type = ComponentType.TEXT,
            text = "Username",
            color = "#0061A4",
            placeholder = "Enter username"
        )

        val fakeRepository = FakeUiRepository().apply {
            setSuccessResult(expected)
        }

        val useCase = GetUiComponentUseCase(fakeRepository, testDispatcher)
        val viewModel = DynamicUiViewModel(useCase)

        viewModel.fetchData()

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state is UiState.Success)
        assertEquals(expected, (state as UiState.Success).component)
    }

    @Test
    fun `fetchData error`() = runTest {
        val fakeRepository = FakeUiRepository().apply {
            setErrorResult("Network error")
        }

        val useCase = GetUiComponentUseCase(fakeRepository, testDispatcher)
        val viewModel = DynamicUiViewModel(useCase)

        viewModel.fetchData()

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state is UiState.Error)
        assertEquals("Network error", (state as UiState.Error).message)
    }

    @Test
    fun `multiple fetchData calls`() = runTest {
        val fakeRepository = FakeUiRepository()
        val useCase = GetUiComponentUseCase(fakeRepository, testDispatcher)
        val viewModel = DynamicUiViewModel(useCase)

        val first = UiComponent(ComponentType.TEXT, "First")
        val second = UiComponent(ComponentType.NUMBER, "Second")

        fakeRepository.setSuccessResult(first)
        viewModel.fetchData()
        advanceUntilIdle()

        assertEquals("First", (viewModel.uiState.value as UiState.Success).component.text)

        fakeRepository.setSuccessResult(second)
        viewModel.fetchData()
        advanceUntilIdle()

        assertEquals("Second", (viewModel.uiState.value as UiState.Success).component.text)
    }
}

class FakeUiRepository : UiRepository {

    private var success: UiComponent? = null
    private var error: String? = null

    fun setSuccessResult(component: UiComponent) {
        success = component
        error = null
    }

    fun setErrorResult(message: String?) {
        error = message
        success = null
    }

    override suspend fun getUiComponent(): Result<UiComponent> {
        return when {
            success != null -> Result.success(success!!)
            error != null -> Result.failure(Exception(error))
            else -> Result.failure(Exception("No result set"))
        }
    }
}