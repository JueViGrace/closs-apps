package org.closs.core.types.picker.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PickerDto(
    @SerialName("name")
    val name: String,
    @SerialName("almacen")
    val almacen: String,
)
