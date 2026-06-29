package org.mohsen.reviewtask.data.repository

import org.mohsen.reviewtask.data.mapper.toDomain
import org.mohsen.reviewtask.data.source.remote.base.HTTPException
import org.mohsen.reviewtask.data.source.remote.base.TypedResult
import org.mohsen.reviewtask.data.source.remote.dataSources.RemoteDataSource
import org.mohsen.reviewtask.domain.model.UiComponent
import org.mohsen.reviewtask.domain.repository.UiRepository

class UiRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : UiRepository {
    override suspend fun getUiComponent(): Result<UiComponent> {
        return when (val result = remoteDataSource.getUiComponent()) {
            is TypedResult.Success -> Result.success(result.value.toDomain())
            is TypedResult.Error -> Result.failure(Exception(result.error.message))
        }
    }
}
