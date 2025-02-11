package org.closs.core.types.shared.state

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.unknown_error

sealed class RequestState<out T> {
    data object Idle : RequestState<Nothing>()
    data object Loading : RequestState<Nothing>()
    data class Success<T>(val data: T) : RequestState<T>()
    data class Error(val error: ResponseMessage) : RequestState<Nothing>()

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isError() = this is Error

    /**
     * Returns data from a [Success].
     * @throws ClassCastException If the current state is not [Success]
     *  */
    fun getSuccessData() = (this as Success).data
    fun getSuccessDataOrNull(): T? {
        return try {
            (this as Success).data
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Returns an error message from an [Error]
     * @throws ClassCastException If the current state is not [Error]
     *  */
    fun getErrorMessage() = (this as Error).error
    fun getErrorMessageOrEmpty(): ResponseMessage {
        return try {
            (this as Error).error
        } catch (e: Exception) {
            ResponseMessage(
                message = Res.string.unknown_error,
                description = e.message ?: ""
            )
        }
    }
}

@Composable
fun<T> RequestState<T>.DisplayResult(
    onIdle: (@Composable () -> Unit)? = null,
    onLoading: @Composable () -> Unit,
    onSuccess: @Composable (T) -> Unit,
    onError: @Composable (ResponseMessage) -> Unit,
    transitionSpec: AnimatedContentTransitionScope<*>.() -> ContentTransform = {
        fadeIn(tween(durationMillis = 300)) togetherWith
            fadeOut(tween(durationMillis = 300))
    }
) {
    AnimatedContent(
        targetState = this,
        transitionSpec = transitionSpec,
        label = "Animated State"
    ) { state ->
        when (state) {
            is RequestState.Idle -> {
                onIdle?.invoke()
            }

            is RequestState.Loading -> {
                onLoading()
            }

            is RequestState.Success -> {
                onSuccess(state.getSuccessData())
            }

            is RequestState.Error -> {
                onError(state.getErrorMessage())
            }
        }
    }
}
