package org.closs.core.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object Splash : Destination

    @Serializable
    data object HomeGraph : Destination

    @Serializable
    data object AuthGraph : Destination

    @Serializable
    data object SignIn : Destination

    @Serializable
    data object Accounts : Destination

    @Serializable
    data object ForgotPassword : Destination

    @Serializable
    data object Home : Destination

    @Serializable
    data object Profile : Destination

    @Serializable
    data object Settings : Destination

    @Serializable
    data object Notifications : Destination

    @Serializable
    data object Users : Destination

    @Serializable
    data class UserDetails(val id: String) : Destination

    @Serializable
    data object Products : Destination

    @Serializable
    data class ProductDetails(val id: String) : Destination

    @Serializable
    data object Cart : Destination

    @Serializable
    data object Checkout : Destination

    @Serializable
    data object Orders : Destination

    @Serializable
    data class OrderDetails(val id: String) : Destination
}
