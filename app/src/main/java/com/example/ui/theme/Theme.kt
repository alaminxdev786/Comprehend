package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = BanglaDarkPrimary,
    onPrimary = BanglaDarkOnPrimary,
    primaryContainer = BanglaDarkPrimaryContainer,
    onPrimaryContainer = BanglaDarkOnPrimaryContainer,
    secondary = BanglaDarkSecondary,
    onSecondary = BanglaDarkOnSecondary,
    secondaryContainer = BanglaDarkSecondaryContainer,
    onSecondaryContainer = BanglaDarkOnSecondaryContainer,
    tertiary = BanglaDarkTertiary,
    onTertiary = BanglaDarkOnTertiary,
    tertiaryContainer = BanglaDarkTertiaryContainer,
    onTertiaryContainer = BanglaDarkOnTertiaryContainer,
    background = BanglaDarkBackground,
    onBackground = BanglaDarkOnBackground,
    surface = BanglaDarkSurface,
    onSurface = BanglaDarkOnSurface,
    surfaceVariant = BanglaDarkSurfaceVariant,
    onSurfaceVariant = BanglaDarkOnSurfaceVariant,
    outline = BanglaDarkOutline
)

private val LightColorScheme = lightColorScheme(
    primary = BanglaGreenPrimary,
    onPrimary = BanglaGreenOnPrimary,
    primaryContainer = BanglaGreenPrimaryContainer,
    onPrimaryContainer = BanglaGreenOnPrimaryContainer,
    secondary = BanglaCrimsonSecondary,
    onSecondary = BanglaCrimsonOnSecondary,
    secondaryContainer = BanglaCrimsonSecondaryContainer,
    onSecondaryContainer = BanglaCrimsonOnSecondaryContainer,
    tertiary = BanglaOchreTertiary,
    onTertiary = BanglaOchreOnTertiary,
    tertiaryContainer = BanglaOchreTertiaryContainer,
    onTertiaryContainer = BanglaOchreOnTertiaryContainer,
    background = BanglaLightBackground,
    onBackground = BanglaLightOnBackground,
    surface = BanglaLightSurface,
    onSurface = BanglaLightOnSurface,
    surfaceVariant = BanglaLightSurfaceVariant,
    onSurfaceVariant = BanglaLightOnSurfaceVariant,
    outline = BanglaLightOutline
)

@Composable
fun ComprehendTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep intentional Bangladesh theme aesthetic
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

  MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
  )
}

// Backward compatibility alias
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
  ComprehendTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
