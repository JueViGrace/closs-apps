package org.closs.core.types.state

import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.unknown_error
import org.jetbrains.compose.resources.StringResource

sealed class DataCodes(val code: AppCodes, val message: StringResource? = null, val description: String? = null) {
    data class CustomMessage(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.Message,
        message = msg,
        description = desc
    )
    data class Ok(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.Ok,
        message = msg,
        description = desc
    )
    data class Created(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.Created,
        message = msg,
        description = desc
    )
    data class Accepted(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.Accepted,
        message = msg,
        description = desc
    )
    data class NoContent(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.NoContent,
        message = msg,
        description = desc
    )
    data class BadRequest(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.BadRequest,
        message = msg,
        description = desc
    )
    data class Unauthorized(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.Unauthorized,
        message = msg,
        description = desc
    )
    data class Forbidden(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.Forbidden,
        message = msg,
        description = desc
    )
    data class NotFound(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.NotFound,
        message = msg,
        description = desc
    )
    data class MethodNotAllowed(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.MethodNotAllowed,
        message = msg,
        description = desc
    )
    data class NotAcceptable(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.NotAcceptable,
        message = msg,
        description = desc
    )
    data class RequestTimeout(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.RequestTimeout,
        message = msg,
        description = desc
    )
    data class Conflict(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.Conflict,
        message = msg,
        description = desc
    )
    data class UnsupportedMediaType(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.UnsupportedMediaType,
        message = msg,
        description = desc
    )
    data class InternalServerError(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.InternalServerError,
        message = msg,
        description = desc
    )
    data class ServiceUnavailable(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.ServiceUnavailable,
        message = msg,
        description = desc
    )
    data class UnexpectedError(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.UnexpectedError,
        message = msg,
        description = desc
    )
    data class DatabaseError(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.DatabaseError,
        message = msg,
        description = desc
    )
    data class NullError(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.NullError,
        message = msg,
        description = desc
    )
    data class VersionError(
        val msg: StringResource? = null,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.VersionError,
        message = msg,
        description = desc
    )
    data class UnknownError(
        val msg: StringResource = Res.string.unknown_error,
        val desc: String? = null
    ) : DataCodes(
        code = AppCodes.UnknownError,
        message = msg,
        description = desc
    )

    companion object {
        fun fromCode(code: AppCodes): DataCodes {
            return DataCodes::class.sealedSubclasses
                .firstOrNull { it.objectInstance?.code?.value == code.value }
                ?.objectInstance ?: UnknownError()
        }
    }
}
