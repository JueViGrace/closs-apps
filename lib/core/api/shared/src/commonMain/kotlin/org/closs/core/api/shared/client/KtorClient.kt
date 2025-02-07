package org.closs.core.api.shared.client

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
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.json.Json
import org.closs.core.types.shared.common.log
import kotlin.coroutines.coroutineContext

interface KtorClient {
    val defaultBaseUrl: String

    fun client(baseUrl: String? = null): HttpClient = HttpClient(CIO) {
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
            url(urlString = baseUrl ?: this@KtorClient.defaultBaseUrl)
        }
    }
}

suspend inline fun <reified T> KtorClient.call(callback: KtorClient.() -> APIResponse<T>): ApiOperation<T> {
    return try {
        val response: APIResponse<T> = callback()
        when (response.status) {
            HttpStatusCode.ServiceUnavailable.value -> {
                ApiOperation.Failure(
                    error = response,
                )
            }
            HttpStatusCode.InternalServerError.value -> {
                ApiOperation.Failure(
                    error = response
                )
            }
            HttpStatusCode.NotFound.value -> {
                ApiOperation.Failure(
                    error = response
                )
            }
            HttpStatusCode.Unauthorized.value -> {
                ApiOperation.Failure(
                    error = response
                )
            }
            HttpStatusCode.Forbidden.value -> {
                ApiOperation.Failure(
                    error = response
                )
            }
            HttpStatusCode.BadRequest.value -> {
                ApiOperation.Failure(
                    error = response
                )
            }
            HttpStatusCode.Conflict.value -> {
                ApiOperation.Failure(
                    error = response
                )
            }
            else -> {
                ApiOperation.Success(value = response)
            }
        }
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        e.log("Network call exception")
        ApiOperation.Failure(
            error = APIResponse(
                status = HttpStatusCode.InternalServerError.value,
                description = HttpStatusCode.InternalServerError.description,
                data = null,
                message = e.message
            ),
        )
    }
}

suspend inline fun<reified T> KtorClient.get(
    baseUrl: String? = null,
    urlString: String,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json
): T {
    return client(baseUrl = baseUrl)
        .get(urlString = urlString) {
            headers.forEach { (key, value) ->
                headers {
                    append(key, value)
                }
            }
            contentType(contentType)
        }
        .body<T>()
}

suspend inline fun<reified T, reified R> KtorClient.post(
    baseUrl: String? = null,
    urlString: String,
    body: T,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json
): R {
    return client(baseUrl = baseUrl)
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

suspend inline fun <reified T, reified R> KtorClient.put(
    baseUrl: String? = null,
    urlString: String,
    body: T,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json
): R {
    return client(baseUrl = baseUrl)
        .put(urlString = urlString) {
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
    baseUrl: String? = null,
    urlString: String,
    body: T,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json
): R {
    return client(baseUrl = baseUrl)
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
    baseUrl: String? = null,
    urlString: String,
    body: T,
    headers: Map<String, String> = emptyMap(),
    contentType: ContentType = ContentType.Application.Json
): R {
    return client(baseUrl = baseUrl)
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
