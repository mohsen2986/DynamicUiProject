package org.mohsen.reviewtask.domain.repository

import org.mohsen.reviewtask.domain.model.UiComponent

interface UiRepository {
    suspend fun getUiComponent(): Result<UiComponent>
}
