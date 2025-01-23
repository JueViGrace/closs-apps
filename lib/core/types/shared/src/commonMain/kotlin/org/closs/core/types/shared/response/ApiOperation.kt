package org.closs.core.types.shared.response

import org.closs.core.types.shared.state.DataCodes

sealed interface ApiOperation<out T> {
    data class Success<T>(val value: APIResponse<T>) : ApiOperation<T>
    data class Failure(val error: DataCodes) : ApiOperation<Nothing>
}

/*
inline fun <reified T> ApiOperation<T>.resolve(): RequestState<T> {
    return when (this) {
        is ApiOperation.Failure -> {
            RequestState.Error(
                error = error.res
            )
        }
        is ApiOperation.Success -> {
            if (value.data == null) {
                return RequestState.Error(
                    error = DataCodes.NullError().res
                )
            }

            RequestState.Success(
                data = value.data
            )
        }
    }
}
*/
