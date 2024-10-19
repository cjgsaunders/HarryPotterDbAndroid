package com.example.harrypotterapp.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val LocalColorScheme = compositionLocalOf<MyColor> {
    error("No color scheme provided")
}

interface MyColor {
    val startBackground: Color
    val endBackground: Color
    val cardBackground: Color
    val surfaceBackground: Color
    val textColor: Color
}

object LightMode : MyColor {
    override val startBackground = Color(0xFFFBEAE5)
    override val endBackground = Color(0xFFE5F5FB)
    override val cardBackground = Color(0xFFEAE5FB)
    override val surfaceBackground = Color(0xFF888888)
    override val textColor: Color = Color(0xFF000000)
}

object DarkMode : MyColor {
    override val startBackground = Color(0xFF111111)
    override val endBackground = Color(0xFF29292D)
    override val cardBackground = Color(0xFF484a4d)
    override val surfaceBackground = Color(0xFF282828)
    override val textColor: Color = Color(0xFFFFFFFF)
}


@Composable
fun getColorScheme(): MyColor {
    return if (isSystemInDarkTheme()) {
        DarkMode
    } else {
        LightMode
    }
}
