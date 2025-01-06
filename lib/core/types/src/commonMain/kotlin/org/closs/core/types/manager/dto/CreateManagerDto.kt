package org.closs.core.shared.types.manager

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateManagerDto(
    @SerialName("kng_codgcia")
    val kngCodgcia: String,
    @SerialName("kng_codcoord")
    val kngCodcoord: String,
)
