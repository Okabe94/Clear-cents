package com.okabe.clearcents.ui.theme

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

//private val DarkColorScheme = darkColorScheme(
//    primary = Purple80,
//    secondary = PurpleGrey80,
//    tertiary = Pink80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Purple40,
//    secondary = PurpleGrey40,
//    tertiary = Pink40
//
//    /* Other default colors to override
//    background = Color(0xFFFFFBFE),
//    surface = Color(0xFFFFFBFE),
//    onPrimary = Color.White,
//    onSecondary = Color.White,
//    onTertiary = Color.White,
//    onBackground = Color(0xFF1C1B1F),
//    onSurface = Color(0xFF1C1B1F),
//    */
//)
private val DarkColorScheme = darkColorScheme(
    primary = Green80, // Example: A vibrant green for primary actions
    onPrimary = Green20,
    primaryContainer = Green30,
    onPrimaryContainer = Green90,
    inversePrimary = Green40,
    secondary = DarkGreen80, // Example: A darker green for secondary elements
    onSecondary = DarkGreen20,
    secondaryContainer = DarkGreen30,
    onSecondaryContainer = DarkGreen90,
    tertiary = Violet80, // Example: A contrasting violet for accents
    onTertiary = Violet20,
    tertiaryContainer = Violet30,
    onTertiaryContainer = Violet90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Grey10,
    onBackground = Grey90,
    surface = Grey10, // Or a slightly different shade like Grey20
    onSurface = Grey90,
    surfaceVariant = GreenGrey30,
    onSurfaceVariant = GreenGrey80,
    inverseSurface = Grey90,
    inverseOnSurface = Grey20,
    outline = GreenGrey60,
    outlineVariant = GreenGrey30, // Softer outline
    scrim = Color.Black,
    surfaceBright = Grey20, // Brighter surface for specific elements
    surfaceContainer = Grey15, // Container slightly above background
    surfaceContainerHigh = Grey25,
    surfaceContainerHighest = Grey30,
    surfaceContainerLow = Grey05,
    surfaceContainerLowest = Grey00, // Base background
    surfaceDim = Grey08 // For dimming effect
)

private val LightColorScheme = lightColorScheme(
    primary = Green40,
    onPrimary = Color.White,
    primaryContainer = Green90,
    onPrimaryContainer = Green10,
    inversePrimary = Green80,
    secondary = DarkGreen40,
    onSecondary = Color.White,
    secondaryContainer = DarkGreen90,
    onSecondaryContainer = DarkGreen10,
    tertiary = Violet40,
    onTertiary = Color.White,
    tertiaryContainer = Violet90,
    onTertiaryContainer = Violet10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Grey99,
    onBackground = Grey10,
    surface = Grey99, // Or a slightly different shade like Grey98
    onSurface = Grey10,
    surfaceVariant = GreenGrey90,
    onSurfaceVariant = GreenGrey30,
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    outline = GreenGrey50,
    outlineVariant = GreenGrey80, // Softer outline
    scrim = Color.Black, // Keep as black or a dark scrim color
    surfaceBright = Grey98,
    surfaceContainer = Grey94,
    surfaceContainerHigh = Grey92,
    surfaceContainerHighest = Grey90,
    surfaceContainerLow = Grey96,
    surfaceContainerLowest = Color.White, // Pure white for base
    surfaceDim = Grey87
)


@Composable
fun ClearCentsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
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