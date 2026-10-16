package dcc.uchile.com.example.petlove.ui.theme

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
    primary = Color(0xFFDD5A21),
    secondary = Color(0xFF707C4A),
    tertiary = Color(0xFF922500),
    onPrimary = Color(0xFF350000),
    onSecondary = Color(0xFF350000),
    onTertiary = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFDD5A21),
    secondary = Color(0xFF707C4A),
    tertiary = Color(0xFF922500),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onSurface = Color(0xFF350000),
    onSurfaceVariant = Color(0xFF922500),
    outline = Color(0xFF707C4A)
)

@Composable
fun PetLoveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}