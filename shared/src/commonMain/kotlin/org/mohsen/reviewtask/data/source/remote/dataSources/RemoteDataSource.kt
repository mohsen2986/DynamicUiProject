package org.mohsen.reviewtask.data.source.remote.dataSources

import org.mohsen.reviewtask.data.model.ComponentResponseDto
import org.mohsen.reviewtask.data.source.remote.base.HTTPException
import org.mohsen.reviewtask.data.source.remote.base.TypedResult

interface RemoteDataSource {
    suspend fun getUiComponent(): TypedResult<ComponentResponseDto, HTTPException>
}
