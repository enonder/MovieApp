package com.elifnuronder.movieapp.ui.theme

import android.app.Activity
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

private val DarkColorScheme = darkColorScheme(
    primary = VibrantOrange,                    // Electric orange
    secondary = VibrantBlue,                    // Bright cyan blue
    tertiary = VibrantGreen,                    // Neon mint green
    background = RichDarkBackground,            // Rich black
    surface = RichDarkSurface,                  // Elevated dark surface
    surfaceVariant = RichDarkCard,              // Card surfaces
    primaryContainer = VibrantPurple,           // Vivid purple containers
    secondaryContainer = VibrantPink,           // Hot pink containers
    onPrimary = Color.Black,                    // Black text on vibrant colors
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.White,                 // White text on dark
    onSurface = Color.White,
    onSurfaceVariant = Color(0xFFE0E0E0),      // Light gray text
    outline = VibrantBlue                       // Bright borders
)

private val LightColorScheme = lightColorScheme(
    primary = CinemaOrange,
    secondary = CinemaAmber,
    tertiary = CinemaWarm,
    background = CinemaBackground,
    surface = CinemaSurface,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun MovieAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+ but disabled because I want a customized app
    // with consistent orange cinema branding across all devices and Android versions
    dynamicColor: Boolean = false,
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