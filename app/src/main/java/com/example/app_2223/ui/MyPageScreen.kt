package com.example.app_2223.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_2223.R
import com.example.app_2223.ui.theme.SurfaceColor

@Composable
fun MyPageScreen(
    onNavigateToLikedFestivals: () -> Unit
) {
    var userName by remember { mutableStateOf("홍길동") }
    var userEmail by remember { mutableStateOf("hong@example.com") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 프로필 영역
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            // 동그란 프로필 이미지 (임시 아이콘)
            Image(
                painter = painterResource(id = R.drawable.ic_mypage),
                contentDescription = "프로필 이미지",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
        }

        // 사용자 정보
        Text(
            text = userName,
            style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp, color = SurfaceColor),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = userEmail,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 내가 좋아요 누른 축제 목록 보기 버튼
        OutlinedButton(
            onClick = onNavigateToLikedFestivals,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text(text = "내가 좋아요 누른 축제 목록")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { /* TODO: 설정 화면 진입 로직 */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "설정",
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(text = "설정")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: 로그아웃 로직 */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "로그아웃")
        }
    }
}
