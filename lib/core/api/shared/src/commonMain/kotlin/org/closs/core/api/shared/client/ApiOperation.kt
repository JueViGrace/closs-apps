package org.closs.core.api.shared.client

sealed interface ApiOperation<out T> {
    data class Success<T>(val value: APIResponse<T>) : ApiOperation<T>
    data class Failure(val error: APIResponse<*>) : ApiOperation<Nothing>
}
