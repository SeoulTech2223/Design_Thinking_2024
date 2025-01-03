package com.example.app_2223.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun MyPageScreen() {
    // 간단한 가상 사용자 정보 (실제로는 ViewModel/서버에서 받아올 수도 있음)
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
            // 동그란 프로필 이미지 (예시 아이콘 사용)
            Image(
                painter = painterResource(id = R.drawable.ic_mypage), // 실제 프로필 이미지 리소스나 URL 사용
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

        // 간단한 버튼들 (로그아웃, 설정 등)
        Button(
            onClick = { /* TODO: 로그아웃 로직 */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "로그아웃")
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
            Text(text = "설정")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 다른 항목들 (예: 알림 설정, 개인정보 수정 등) 원하는 만큼 추가
        // ...
    }
}
