package org.mohsen.reviewtask.data.source.remote.base

import co.touchlab.kermit.Logger
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.JsonConvertException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

suspend inline fun <reified R> safeCall(
    crossinline call: suspend () -> HttpResponse
): TypedResult<R, HTTPException> {
    val response = try {
        call()
    } catch (e: UnresolvedAddressException) {
        Logger.e("UnresolvedAddressException: ${e.message}")
        return TypedResult.Error(HTTPException(503, "Service Unavailable"))
    } catch (e: SerializationException) {
        Logger.e("SerializationException: ${e.message}")
        return TypedResult.Error(HTTPException(500, "SerializationException"))
    } catch (e: NoTransformationFoundException) {
        Logger.e("NoTransformationFoundException: ${e.message}")
        return TypedResult.Error(
            HTTPException(
                500, "N" +
                        "oTransformationFoundException"
            )
        )
    } catch (e: Exception) {
        Logger.e("Exception: ${e.message}")
        return TypedResult.Error(HTTPException(500, "Exception ${e.message}"))
    }

    return try {
        responseToResult(response)
    } catch (e: JsonConvertException) {
        Logger.e("JsonConvertException: ${e.message}")
        TypedResult.Error(HTTPException(500, "Failed to parse response body: ${e.message}"))
    } catch (e: Exception) {
        Logger.e("Exception: ${e.message}")
        TypedResult.Error(HTTPException(500, "Unexpected error: ${e.message}"))
    }
}

suspend inline fun <reified R> responseToResult(
    response: HttpResponse
): TypedResult<R, HTTPException> =
    when (response.status.value) {
        in 200..299 -> TypedResult.Success(response.body<R>(), response.status.value)

        in 300..399 -> TypedResult.Error(
            HTTPException(
                response.status.value,
                response.status.description
            )
        )

        in 400..499 -> TypedResult.Error(
            HTTPException(
                response.status.value,
                response.status.description
            )
        )

        in 500..599 -> TypedResult.Error(
            HTTPException(
                response.status.value,
                response.status.description
            )
        )

        else -> TypedResult.Error(HTTPException(response.status.value, response.status.description))
    }


sealed class TypedResult<out S, out E> {
    data class Success<out S>(val value: S, val httpStatusCode: Int) : TypedResult<S, Nothing>()
    data class Error<out E>(val error: E) : TypedResult<Nothing, E>()
}

//data class HTTPException(
//    val code: Int,
//    val message: String
//)