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
fun calculateLabelFontSize(): TextUnit {
    return when (getScreenSize()) {
        ScreenSize.Compact -> MaterialTheme.typography.labelSmall.fontSize
        ScreenSize.Medium -> MaterialTheme.typography.labelLarge.fontSize
        ScreenSize.Large -> MaterialTheme.typography.bodySmall.fontSize
    }
}

@Composable
fun calculateLabelFontWeight(): FontWeight? {
    return when (getScreenSize()) {
        ScreenSize.Compact -> MaterialTheme.typography.labelSmall.fontWeight
        ScreenSize.Medium -> MaterialTheme.typography.labelMedium.fontWeight
        ScreenSize.Large -> MaterialTheme.typography.bodySmall.fontWeight
    }
}

@Composable
fun String.calculateMaxLength(): String {
    return when (getScreenOrientation()) {
        Orientation.Portrait -> when (getScreenSize()) {
            ScreenSize.Compact -> {
                if (this.length > 22) {
                    "${this.slice(0..22)}..."
                } else {
                    this
                }
            }
            ScreenSize.Medium -> {
                if (this.length > 38) {
                    "${this.slice(0..38)}..."
                } else {
                    this
                }
            }
            ScreenSize.Large -> {
                if (this.length > 52) {
                    "${this.slice(0..52)}..."
                } else {
                    this
                }
            }
        }
        Orientation.Landscape -> when (getScreenSize()) {
            ScreenSize.Compact -> {
                if (this.length > 68) {
                    "${this.slice(0..68)}..."
                } else {
                    this
                }
            }
            ScreenSize.Medium -> {
                if (this.length > 78) {
                    "${this.slice(0..78)}..."
                } else {
                    this
                }
            }
            ScreenSize.Large -> {
                if (this.length > 88) {
                    "${this.slice(0..88)}..."
                } else {
                    this
                }
            }
        }
    }
}
