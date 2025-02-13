package org.closs.order.presentation.history.events

sealed interface HistoryEvents {
    data class NavigateToDetails(val id: String) : HistoryEvents
}
