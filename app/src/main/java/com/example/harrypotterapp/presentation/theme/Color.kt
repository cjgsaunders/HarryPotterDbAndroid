package com.example.harrypotterapp.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalColorScheme =
    compositionLocalOf<MyColor> {
        error("No color scheme provided")
    }

interface MyColor {
    val startBackground: Color
    val endBackground: Color
    val cardBackground: Color
    val surfaceBackground: Color
    val textColor: Color
    val searchBoxBackground: Color
    val searchBoxPlaceholderText: Color
    val topAppBarColor: Color
    val noHouseStartGradient: Color
    val dataSubtitle: Color
}

@Suppress("MagicNumber")
object LightMode : MyColor {
    override val startBackground = Color(0xFFFBEAE5)
    override val endBackground = Color(0xFFE5F5FB)
    override val cardBackground = Color(0xFFE8DDDD)
    override val surfaceBackground = Color(0xFF888888)
    override val textColor: Color = Color(0xFF000000)
    override val searchBoxBackground: Color = Color(0xFFE2DEE7)
    override val searchBoxPlaceholderText: Color = Color(0xFF3D3D3D)
    override val topAppBarColor: Color = Color(0xFF6886FF)
    override val noHouseStartGradient: Color = Color(0xFFF1F1F1)
    override val dataSubtitle: Color = Color(0xFF424040)
}

@Suppress("MagicNumber")
object DarkMode : MyColor {
    override val startBackground = Color(0xFF111111)
    override val endBackground = Color(0xFF29292D)
    override val cardBackground = Color(0xFF3c3e44)
    override val surfaceBackground = Color(0xFF282828)
    override val textColor: Color = Color(0xFFFFFFFF)
    override val searchBoxBackground: Color = Color(0xFF6A686B)
    override val searchBoxPlaceholderText: Color = Color(0xFFA1A1A1)
    override val topAppBarColor: Color = Color(0xFF051A36)
    override val noHouseStartGradient: Color = Color(0xFF000000)
    override val dataSubtitle: Color = Color(0xFF9E9B98)
}

@Composable
fun getColorScheme(): MyColor =
    if (isSystemInDarkTheme()) {
        DarkMode
    } else {
        LightMode
    }
