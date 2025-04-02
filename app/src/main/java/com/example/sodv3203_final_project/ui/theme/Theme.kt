package com.example.sodv3203_final_project.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = TimsRed,
    secondary = TimsYellow,
    background = TimsCream,
    surface = TimsCream,
    onPrimary = TimsTextLight,
    onSecondary = TimsTextDark,
    onBackground = TimsTextDark,
    onSurface = TimsTextDark
)

private val DarkColors = darkColorScheme(
    primary = TimsRed,
    secondary = TimsYellow,
    background = Color(0xFF2B2B2B),
    surface = Color(0xFF1E1E1E),
    onPrimary = TimsTextLight,
    onSecondary = TimsTextDark,
    onBackground = TimsTextLight,
    onSurface = TimsTextLight
)

@Composable
fun SODV3203_Final_ProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Turn off dynamic colors to always use Tims colors
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColors
        else -> LightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
