package org.closs.auth.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle

@Composable
fun HaveAnAccountComponent(
    modifier: Modifier = Modifier.fillMaxWidth(),
    initialText: String,
    screenText: String,
    onClick: () -> Unit,
) {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
            append(initialText)
        }
        val link = LinkAnnotation.Clickable(
            tag = screenText,
            styles = TextLinkStyles(
                style = SpanStyle(color = MaterialTheme.colorScheme.secondary),
                focusedStyle = SpanStyle(color = MaterialTheme.colorScheme.primary),
                hoveredStyle = SpanStyle(color = MaterialTheme.colorScheme.primary),
                pressedStyle = SpanStyle(color = MaterialTheme.colorScheme.inversePrimary)
            ),
            linkInteractionListener = {
                onClick()
            }
        )
        withLink(link) {
            pushStringAnnotation(tag = screenText, annotation = screenText)
            append(screenText)
        }
    }

    BasicText(
        modifier = modifier,
        text = text,
    )
}
