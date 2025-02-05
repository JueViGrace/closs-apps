package org.closs.core.presentation.shared.ui.components.display

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import org.closs.core.presentation.shared.utils.calculateDefaultFontSize
import org.closs.core.presentation.shared.utils.calculateDefaultFontWeight

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = calculateDefaultFontSize(),
    fontWeight: FontWeight? = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = 2,
    color: Color = LocalContentColor.current,
    textDecoration: TextDecoration = TextDecoration.None,
    softWrap: Boolean = true,
    overflow: TextOverflow = TextOverflow.Clip
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        textAlign = textAlign,
        maxLines = maxLines,
        color = color,
        modifier = modifier,
        textDecoration = textDecoration,
        softWrap = softWrap,
        overflow = overflow
    )
}

