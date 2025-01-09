package com.example.app_2223.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_2223.data.Festival

data class Post(
    val userName: String,
    val content: String,
    val time: String, // 예: "2025-01-10 09:30"
)

/**
 * 축제 상세 화면
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FestivalDetailScreen(
    festival: Festival,
    isLiked: Boolean,
    likeCount: Int,
    onToggleLike: (Festival) -> Unit,
    onBackClick: () -> Unit
) {
    // 간단한 게시판 Post 목록 (실제로는 서버나 DB에서 불러옴)
    val initialPosts = remember {
        mutableStateListOf(
            Post("김철수", "${festival.name}에 사람 너무 많아요", "2025-01-10 10:00"),
            Post("박수아", "행사가 미뤄 졌대요", "2025-01-09 18:20"),
            Post("이정민", "오늘 공연진이 변경되었다고 하네요?", "2025-01-09 17:20"),
            Post("전진욱", "입구 쪽에 파는 닭꼬치 맛있어요", "2025-01-09 15:30"),
            Post("박영희", "체험 부스존 대기 줄 30분 이상 걸리는 것 같아요", "2025-01-09 12:15"),
        )
    }

    var newComment by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            // AppBar 배경 흰색, 글자/아이콘 검정색
            TopAppBar(
                title = { Text(text = festival.name) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,     // 배경 흰색
                    titleContentColor = Color.Black,   // 제목 글자색 검정
                    navigationIconContentColor = Color.Black, // 뒤로가기 아이콘 색 검정
                    actionIconContentColor = Color.Black
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 좋아요 버튼 및 좋아요 수
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "좋아요: $likeCount",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold // 글자 굵게
                    ),
                    fontSize = 15.sp
                )
                Spacer(modifier = Modifier.width(5.dp))
                Button(
                    onClick = { onToggleLike(festival) },
                    shape = CircleShape, // 둥근 모양
                    border = BorderStroke(1.dp, Color.Black), // 검정 테두리
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isLiked) Color(0xFFFF224A) else Color.White
                    ),
                    contentPadding = PaddingValues(0.dp), // 내부 여백 최소화
                    modifier = Modifier
                        .size(30.dp) // 버튼 자체 크기
                ) {
                }
            }

            // 게시판
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                reverseLayout = true // 최신글이 상단에 보이도록
            ) {
                items(initialPosts.asReversed()) { post ->
                    // 댓글 카드 부분을 홈 탭과 동일한 스타일로 적용
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFDDDDDD)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = "${post.userName} (${post.time})",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = post.content)
                        }
                    }
                }
            }

            // 댓글 작성
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = newComment,
                onValueChange = { newComment = it },
                label = { Text("댓글 작성") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (newComment.isNotBlank()) {
                        initialPosts.add(
                            Post("사용자", newComment, "2025-01-10 11:00")
                        )
                        newComment = ""
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text("등록")
            }
        }
    }
}
