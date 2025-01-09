package com.example.app_2223

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.app_2223.ui.theme.CustomTheme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CustomTheme {
                SplashScreen(
                    onTimeout = {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit, modifier: Modifier = Modifier) {
    LaunchedEffect(Unit) {
        delay(2000) // 2초 대기
        onTimeout()
    }

    // Box에 배경색을 적용
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF839DFF)) // 배경색 설정
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize() // Scaffold 레이아웃
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF839DFF))
                    .padding(padding),
                contentAlignment = Alignment.Center // 중앙 정렬
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally, // 수평 중앙 정렬
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.festival), // drawable의 Festival.png
                        contentDescription = "Festival 이미지",
                        modifier = Modifier
                            .padding(end = 50.dp) // 오른쪽에 padding 추가
                    )
                    Image(
                        painter = painterResource(id = R.drawable.on_air), // drawable의 On Air.png
                        contentDescription = "On Air 이미지",
                        modifier = Modifier
                            .padding(start = 50.dp) // 왼쪽에 padding 추가
                    )
                }
            }
        }
    }
}
