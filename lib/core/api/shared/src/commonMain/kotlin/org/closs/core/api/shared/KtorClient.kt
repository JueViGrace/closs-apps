package org.closs.core.api.shared

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.json.Json
import org.closs.core.types.shared.common.log
import org.closs.core.types.shared.response.APIResponse
import org.closs.core.types.shared.response.ApiOperation
import org.closs.core.types.shared.state.DataCodes
import org.closs.core.types.shared.state.ResponseMessage
import kotlin.coroutines.coroutineContext

interface KtorClient {
    val baseUrl: String

    fun client(urlString: String? = null): HttpClient = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.BODY

            sanitizeHeader { header -> header == HttpHeaders.Authorization }
        }

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                    explicitNulls = true
                }
            )
        }

        install(DefaultRequest) {
            url(urlString = urlString ?: this@KtorClient.baseUrl)
        }
    }
}

suspend inline fun <reified T> KtorClient.call(callback: KtorClient.() -> APIResponse<T>): ApiOperation<T> {
    return try {
        val body: APIResponse<T> = callback()
        when (body.status) {
            HttpStatusCode.ServiceUnavailable.value -> {
                ApiOperation.Failure(
                    error = DataCodes.ServiceUnavailable(
                        res = ResponseMessage(
                            description = body.message
                        )
                    )
                )
            }
            HttpStatusCode.InternalServerError.value -> {
                ApiOperation.Failure(
                    error = DataCodes.InternalServerError(
                        res = ResponseMessage(
                            description = body.message
                        )
                    )
                )
            }
            HttpStatusCode.NotFound.value -> {
                ApiOperation.Failure(
                    error = DataCodes.NotFound(
                        res = ResponseMessage(
                            description = body.message
                        )
                    )
                )
            }
            HttpStatusCode.Unauthorized.value -> {
                ApiOperation.Failure(
                    error = DataCodes.Unauthorized(
                        res = ResponseMessage(
                            description = body.message
                        )
                    )
                )
            }
            HttpStatusCode.Forbidden.value -> {
                ApiOperation.Failure(
                    error = DataCodes.Forbidden(
                        res = ResponseMessage(
                            description = body.message
                        )
                    )
                )
            }
            HttpStatusCode.BadRequest.value -> {
                ApiOperation.Failure(
                    error = DataCodes.BadRequest(
                        res = ResponseMessage(
                            description = body.message
                        )
                    )
                )
            }
            HttpStatusCode.Conflict.value -> {
                ApiOperation.Failure(
                    error = DataCodes.Conflict(
                        res = ResponseMessage(
                            description = body.message
                        )
                    )
                )
            }
            else -> {
                ApiOperation.Success(value = body)
            }
        }
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        e.log("Network call exception")
        ApiOperation.Failure(
            error = DataCodes.UnexpectedError(
                res = ResponseMessage(
                    description = e.message
                )
            )
        )
    }
}

suspend inline fun<reified T> KtorClient.get(
    urlString: String,
    headers: Map<String, String> = emptyMap()
): T {
    return client()
        .get(urlString = urlString) {
            headers.forEach { (key, value) ->
                headers {
                    append(key, value)
                }
            }
        }
        .body<T>()
}

suspend inline fun<reified T, reified R> KtorClient.post(
    urlString: String,
    body: T,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json
): R {
    return client()
        .post(urlString = urlString) {
            headers.forEach { (key, value) ->
                headers {
                    append(key, value)
                }
            }
            contentType(contentType)
            setBody(body)
        }
        .body<R>()
}

suspend inline fun <reified T, reified R> KtorClient.patch(
    urlString: String,
    body: T,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json
): R {
    return client()
        .patch(urlString = urlString) {
            headers.forEach { (key, value) ->
                headers {
                    append(key, value)
                }
            }
            contentType(contentType)
            setBody(body)
        }
        .body<R>()
}

suspend inline fun <reified T, reified R> KtorClient.delete(
    urlString: String,
    body: T,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json
): R {
    return client()
        .delete(urlString = urlString) {
            headers.forEach { (key, value) ->
                headers {
                    append(key, value)
                }
            }
            contentType(contentType)
            setBody(body)
        }
        .body<R>()
}
