package ivan.karpiuk.cicdlearning.ui.theme

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
import ivan.karpiuk.cicdlearning.ui.theme.Colors.Companion.Pink40
import ivan.karpiuk.cicdlearning.ui.theme.Colors.Companion.Pink80
import ivan.karpiuk.cicdlearning.ui.theme.Colors.Companion.Purple40
import ivan.karpiuk.cicdlearning.ui.theme.Colors.Companion.PurpleGrey40
import ivan.karpiuk.cicdlearning.ui.theme.Colors.Companion.PurpleGrey80

private val DarkColorScheme = darkColorScheme(
    primary = Pink40,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
)

@Composable
fun CICDLearningTheme(
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
