package com.rizkimauln.bayani.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Skema Warna Gelap
private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = Accent,
    tertiary = PrimaryLight
)

// Skema Warna Terang (Utama)
private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Accent,
    tertiary = PrimaryLight,
    background = BgBody,
    surface = White,
    onPrimary = White,
    onSecondary = White,
    onTertiary = PrimaryDark,
    onBackground = TextMain,
    onSurface = TextMain
)

@Composable
fun BayaniTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color tersedia di Android 12+
    // Kita set ke FALSE agar warna aplikasi tetap HIJAU/TEAL sesuai desain kamu (tidak mengikuti wallpaper HP)
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        // Jika ingin aplikasi selalu Light Mode (seperti desain HTML),
        // ganti baris bawah jadi: else -> LightColorScheme
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        // Typography menggunakan default bawaan project (biasanya ada di file Type.kt)
        typography = Typography,
        content = content
    )
}

