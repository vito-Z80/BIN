package com.example.bin.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color



private val DarkColors = darkColors(
    primary = primary,
    onPrimary = onPrimary,
    primaryVariant = primaryVariant,
    secondary = secondary,
    onSecondary = onSecondary,
    secondaryVariant = secondaryVariant,
    background = background,
    surface = surface,
    error = error,
    onBackground = onBackground
)

private val LightColors = darkColors(
    primary = primary,
    onPrimary = onPrimary,
    primaryVariant = primaryVariant,
    secondary = secondary,
    onSecondary = onSecondary,
    secondaryVariant = secondaryVariant,
    background = background,
    surface = surface,
    error = error,
    onBackground = onBackground
)

@Composable
fun BINTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}