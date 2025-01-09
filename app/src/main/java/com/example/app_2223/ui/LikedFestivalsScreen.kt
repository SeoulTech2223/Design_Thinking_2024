package com.example.app_2223.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.app_2223.data.Festival

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LikedFestivalsScreen(
    festivals: List<Festival>,
    likeStates: Map<String, Pair<Boolean, Int>>,
    onFestivalClick: (Festival) -> Unit,
    onBackClick: () -> Unit
) {
    // 좋아요 상태가 true인 축제들만 필터링
    val likedFestivals = festivals.filter { fest ->
        likeStates[fest.name]?.first == true
    }

    Scaffold(
        topBar = {
            // AppBar 배경 흰색, 글자/아이콘 검정
            TopAppBar(
                title = { Text("내가 좋아요 누른 축제") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (likedFestivals.isEmpty()) {
            // 좋아요한 축제가 하나도 없을 때
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "아직 좋아요한 축제가 없습니다.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(likedFestivals) { festival ->
                    // 홈 탭 카드 스타일과 동일하게 적용
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFDDDDDD)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        onClick = { onFestivalClick(festival) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = festival.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = festival.location)
                            Text(text = festival.date)
                        }
                    }
                }
            }
        }
    }
}
