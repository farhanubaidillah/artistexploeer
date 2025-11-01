package com.example.artistexplorer.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val GruvboxColorScheme = darkColorScheme(
    background = GruvboxBackground,
    surface = GruvboxSurface,
    primary = GruvboxPrimary,
    onPrimary = GruvboxOnPrimary,
    onBackground = GruvboxText,
    onSurface = GruvboxText,
    error = GruvboxError
)

@Composable
fun ArtistExplorerTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GruvboxColorScheme,
        typography = Typography(),
        content = content
    )
}
