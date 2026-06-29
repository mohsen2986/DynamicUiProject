package org.mohsen.reviewtask.data.source.remote.base

import kotlinx.serialization.Serializable

/**
 * Base response wrapper for API responses that follow the standard format:
 * {
 *   "body": T,
 *   "message": String?
 * }
 */
@Serializable
data class ApiResponse<T>(
    val body: T? = null,
    val message: String? = null
) {
    companion object {
        // Helper function to create a success response
        fun <T> success(data: T, message: String? = null): ApiResponse<T> {
            return ApiResponse(body = data, message = message)
        }

        // Helper function to create an error response
        fun <T> error(message: String, data: T? = null): ApiResponse<T> {
            @Suppress("UNCHECKED_CAST")
            return ApiResponse(body = data as T, message = message)
        }
    }
}