package org.closs.core.api.shared.client

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.closs.core.types.shared.common.Constants

@Serializable
data class APIResponse<T>(
    @SerialName("status")
    val status: Int,
    @SerialName("description")
    val description: String,
    @SerialName("data")
    val data: T? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("time")
    val time: String = Constants.currentTime
)
