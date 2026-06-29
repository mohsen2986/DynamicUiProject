package org.mohsen.reviewtask.data.source.remote.dataSources

import io.ktor.client.HttpClient
import io.ktor.client.request.*
import org.mohsen.reviewtask.data.model.ComponentResponseDto
import org.mohsen.reviewtask.data.source.remote.base.HTTPException
import org.mohsen.reviewtask.data.source.remote.base.safeCall

class KtorRemoteDataSource(
    private val client: HttpClient
) : RemoteDataSource {
    override suspend fun getUiComponent() = safeCall<ComponentResponseDto> {
        client.get("/screen/home")
    }
}
