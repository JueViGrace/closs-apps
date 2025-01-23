package org.closs.core.types.picker

import org.closs.core.types.aliases.DbPicker
import org.closs.core.types.picker.dto.PickerDto

fun Picker.toDbPicker(userId: String): DbPicker = DbPicker(
    user_id = userId,
    name = name,
    almacen = almacen
)

fun PickerDto.toPicker(): Picker = Picker(
    name = name,
    almacen = almacen
)
