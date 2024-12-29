package com.example.app_2223.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColors = lightColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = BackgroundColor,
    surface = SurfaceColor
)

private val DarkColors = darkColorScheme(
    primary = PrimaryColor,
    secondary = SecondaryColor,
    background = Color.Black,
    surface = Color.DarkGray
)

@Composable
fun CustomTheme(
    darkTheme: Boolean = false, // 다크 테마 활성화 여부
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    // SystemUiController를 가져옴
    val systemUiController = rememberSystemUiController()

    // 상태바 색상 설정
    systemUiController.setStatusBarColor(
        color = Color.White, // 상태 바 색상
        darkIcons = !darkTheme  // 상태 바 아이콘 밝기
    )

    MaterialTheme(
        colorScheme = colors,
        typography = CustomTypography,
        content = content
    )
}
