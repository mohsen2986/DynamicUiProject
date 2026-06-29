package org.mohsen.reviewtask.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.mohsen.reviewtask.domain.model.UiComponent
import org.mohsen.reviewtask.domain.repository.UiRepository

class GetUiComponentUseCase(
    private val repository: UiRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): UiComponent = withContext(dispatcher) {
        repository.getUiComponent().getOrThrow()
    }
}
