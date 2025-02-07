package org.closs.order.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.utils.calculateLabelFontSize
import org.closs.core.presentation.shared.utils.calculateLabelFontWeight
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.picked_quantity
import org.closs.core.resources.resources.generated.resources.quantity_empty
import org.closs.core.resources.resources.generated.resources.quantity_exceeds_ordered
import org.closs.core.types.order.OrderLine
import org.jetbrains.compose.resources.stringResource

@Composable
fun PickUpListItem(
    modifier: Modifier = Modifier,
    line: OrderLine,
    onQuantityChange: (String) -> Unit,
    onCheckedChange: (Boolean) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val value = line.cantidad.toString()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            OrderProductItem(
                modifier = Modifier.fillMaxWidth(),
                line = line,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = value,
                    onValueChange = { newValue ->
                        if (newValue.any { !it.isDigit() }) {
                            onQuantityChange(line.cantidad.toString())
                        } else {
                            onQuantityChange(newValue)
                        }
                    },
                    label = {
                        TextComponent(
                            text = stringResource(Res.string.picked_quantity)
                        )
                    },
                    supportingText = when {
                        // todo: move this to viewmodel
                        value.isEmpty() -> {
                            {
                                TextComponent(
                                    text = stringResource(Res.string.quantity_empty),
                                    fontSize = calculateLabelFontSize(),
                                    fontWeight = calculateLabelFontWeight(),
                                )
                            }
                        }
                        value.toInt() > line.cantref -> {
                            {
                                TextComponent(
                                    text = stringResource(Res.string.quantity_exceeds_ordered),
                                    fontSize = calculateLabelFontSize(),
                                    fontWeight = calculateLabelFontWeight(),
                                )
                            }
                        }
                        else -> null
                    },
                    enabled = !line.checked,
                    readOnly = line.checked,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                    isError = value.isEmpty() || value.toInt() > line.cantref,
                )
                Checkbox(
                    checked = line.checked,
                    onCheckedChange = onCheckedChange,
                )
            }
        }

        HorizontalDivider()
    }
}
