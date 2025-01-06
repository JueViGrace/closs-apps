package org.closs.product.domain.rules

import org.closs.product.domain.model.ProductData
import org.jetbrains.compose.resources.StringResource

object ProductValidator {

    fun validate(product: ProductData): ValidationError {
        val errors = ValidationError()

        // TODO: make string resources

        return errors
    }

    data class ValidationError(
        val nameError: StringResource? = null,
        val descriptionError: StringResource? = null,
        val categoryError: StringResource? = null,
        val priceError: StringResource? = null,
        val stockError: StringResource? = null,
        val issuedError: StringResource? = null,
        val hasStockError: StringResource? = null,
        val discountError: StringResource? = null,
        val ratingError: StringResource? = null,
    )
}
