package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme =
  darkColorScheme(
    primary = SheepPrimary,
    secondary = SheepSecondary,
    tertiary = SheepTertiary,
    background = Color(0xFF1E2B4D),
    surface = Color(0xFF2C3E6B),
    onBackground = Color(0xFFE4F3FC),
    onSurface = Color(0xFFE4F3FC)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = SheepPrimary,
    secondary = SheepSecondary,
    tertiary = SheepTertiary,
    background = SheepBackground,
    surface = SheepSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = SheepTextPrimary,
    onSurface = SheepTextPrimary,
    surfaceVariant = SheepCardBg
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Disable dynamic color by default to ensure cute pastel design matches user screenshots
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme =
    when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }

      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
