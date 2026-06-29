package org.mohsen.reviewtask.data.source.remote.base

data class HTTPException(
    val code: Int,
    val message: String
)