package org.closs.core.presentation.shared.ui.components.display

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun<T> LazyColumnComponent(
    modifier: Modifier = Modifier,
    items: List<T> = emptyList(),
    grouped: Map<String?, List<T>>? = null,
    header: (@Composable () -> Unit)? = null,
    stickyHeader: (@Composable (String?) -> Unit)? = null,
    content: @Composable (T) -> Unit,
    footer: (@Composable () -> Unit)? = null,
    lazyListState: LazyListState = rememberLazyListState(),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(15.dp, Alignment.Top),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        state = lazyListState,
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
        contentPadding = contentPadding
    ) {
        if (grouped != null) {
            if (header != null) {
                item {
                    header()
                }
            }
            grouped.forEach { (head, list) ->
                if (stickyHeader != null) {
                    stickyHeader {
                        stickyHeader(head)
                    }
                }

                items(
                    items = list
                ) {
                    content(it)
                }
            }
        } else {
            if (header != null) {
                item {
                    header()
                }
            }

            items(
                items
            ) {
                content(it)
            }
        }

        item {
            if (footer != null) {
                footer()
            }
        }
    }
}
