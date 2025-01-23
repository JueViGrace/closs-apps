package org.closs.core.types.shared.state

sealed class DataCodes(val code: AppCodes, val response: ResponseMessage) {
    data class CustomMessage(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.Message,
        response = res ?: ResponseMessage()
    )
    data class Ok(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.Ok,
        response = res ?: ResponseMessage()
    )
    data class Created(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.Created,
        response = res ?: ResponseMessage()
    )
    data class Accepted(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.Accepted,
        response = res ?: ResponseMessage()
    )
    data class NoContent(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.NoContent,
        response = res ?: ResponseMessage()
    )
    data class BadRequest(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.BadRequest,
        response = res ?: ResponseMessage()
    )
    data class Unauthorized(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.Unauthorized,
        response = res ?: ResponseMessage()
    )
    data class Forbidden(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.Forbidden,
        response = res ?: ResponseMessage()
    )
    data class NotFound(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.NotFound,
        response = res ?: ResponseMessage()
    )
    data class MethodNotAllowed(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.MethodNotAllowed,
        response = res ?: ResponseMessage()
    )
    data class NotAcceptable(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.NotAcceptable,
        response = res ?: ResponseMessage()
    )
    data class RequestTimeout(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.RequestTimeout,
        response = res ?: ResponseMessage()
    )
    data class Conflict(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.Conflict,
        response = res ?: ResponseMessage()
    )
    data class UnsupportedMediaType(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.UnsupportedMediaType,
        response = res ?: ResponseMessage()
    )
    data class InternalServerError(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.InternalServerError,
        response = res ?: ResponseMessage()
    )
    data class ServiceUnavailable(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.ServiceUnavailable,
        response = res ?: ResponseMessage()
    )
    data class UnexpectedError(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.UnexpectedError,
        response = res ?: ResponseMessage()
    )
    data class DatabaseError(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.DatabaseError,
        response = res ?: ResponseMessage()
    )
    data class NullError(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.NullError,
        response = res ?: ResponseMessage()
    )
    data class VersionError(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.VersionError,
        response = res ?: ResponseMessage()
    )
    data class UnknownError(
        val res: ResponseMessage? = null
    ) : DataCodes(
        code = AppCodes.UnknownError,
        response = res ?: ResponseMessage()
    )

    companion object {
        fun fromCode(code: AppCodes): DataCodes {
            return DataCodes::class.sealedSubclasses
                .firstOrNull { it.objectInstance?.code?.value == code.value }
                ?.objectInstance ?: UnknownError()
        }
    }
}
