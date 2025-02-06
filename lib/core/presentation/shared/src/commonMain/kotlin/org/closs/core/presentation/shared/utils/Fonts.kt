package org.closs.core.presentation.shared.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit

@Composable
fun calculateDefaultFontSize(): TextUnit {
    return when (getScreenSize()) {
        ScreenSize.Compact -> MaterialTheme.typography.bodyMedium.fontSize
        ScreenSize.Medium -> MaterialTheme.typography.bodyLarge.fontSize
        ScreenSize.Large -> MaterialTheme.typography.titleMedium.fontSize
    }
}

@Composable
fun calculateDefaultFontWeight(): FontWeight? {
    return when (getScreenSize()) {
        ScreenSize.Compact -> MaterialTheme.typography.bodyMedium.fontWeight
        ScreenSize.Medium -> MaterialTheme.typography.bodyLarge.fontWeight
        ScreenSize.Large -> MaterialTheme.typography.titleMedium.fontWeight
    }
}

@Composable
fun calculateMediumFontSize(): TextUnit {
    return when (getScreenSize()) {
        ScreenSize.Compact -> MaterialTheme.typography.bodyLarge.fontSize
        ScreenSize.Medium -> MaterialTheme.typography.titleMedium.fontSize
        ScreenSize.Large -> MaterialTheme.typography.titleLarge.fontSize
    }
}

@Composable
fun calculateMediumFontWeight(): FontWeight? {
    return when (getScreenSize()) {
        ScreenSize.Compact -> MaterialTheme.typography.bodyLarge.fontWeight
        ScreenSize.Medium -> MaterialTheme.typography.titleMedium.fontWeight
        ScreenSize.Large -> MaterialTheme.typography.titleLarge.fontWeight
    }
}

@Composable
fun calculateLargeFontSize(): TextUnit {
    return when (getScreenSize()) {
        ScreenSize.Compact -> MaterialTheme.typography.titleMedium.fontSize
        ScreenSize.Medium -> MaterialTheme.typography.titleLarge.fontSize
        ScreenSize.Large -> MaterialTheme.typography.displaySmall.fontSize
    }
}

@Composable
fun calculateLargeFontWeight(): FontWeight? {
    return when (getScreenSize()) {
        ScreenSize.Compact -> MaterialTheme.typography.titleMedium.fontWeight
        ScreenSize.Medium -> MaterialTheme.typography.titleLarge.fontWeight
        ScreenSize.Large -> MaterialTheme.typography.displaySmall.fontWeight
    }
}

@Composable
fun calculateSmallFontSize(): TextUnit {
    return when (getScreenSize()) {
        ScreenSize.Compact -> MaterialTheme.typography.labelLarge.fontSize
        ScreenSize.Medium -> MaterialTheme.typography.bodySmall.fontSize
        ScreenSize.Large -> MaterialTheme.typography.bodyMedium.fontSize
    }
}

@Composable
fun calculateSmallFontWeight(): FontWeight? {
    return when (getScreenSize()) {
        ScreenSize.Compact -> MaterialTheme.typography.labelLarge.fontWeight
        ScreenSize.Medium -> MaterialTheme.typography.bodySmall.fontWeight
        ScreenSize.Large -> MaterialTheme.typography.bodyMedium.fontWeight
    }
}

@Composable
fun calculateLabelFontSize(): TextUnit {
    return when (getScreenSize()) {
        ScreenSize.Compact -> MaterialTheme.typography.labelMedium.fontSize
        ScreenSize.Medium -> MaterialTheme.typography.labelLarge.fontSize
        ScreenSize.Large -> MaterialTheme.typography.bodySmall.fontSize
    }
}

@Composable
fun calculateLabelFontWeight(): FontWeight? {
    return when (getScreenSize()) {
        ScreenSize.Compact -> MaterialTheme.typography.labelMedium.fontWeight
        ScreenSize.Medium -> MaterialTheme.typography.labelMedium.fontWeight
        ScreenSize.Large -> MaterialTheme.typography.bodySmall.fontWeight
    }
}
