package com.example.app_2223.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.app_2223.data.Festival
import com.example.app_2223.data.sampleFestivalList
import com.example.app_2223.ui.theme.SecondaryColor

/**
 * 주제별 화면
 * 1) 축제 이름에서 '주제' 키워드를 추출
 * 2) 주제 목록을 선택하면 해당 주제의 축제 목록 표시
 */
@Composable
fun SubjectScreen() {
    // 축제 리스트 전부 불러오기
    val allFestivals = sampleFestivalList

    // 축제 이름에서 'Xxx 축제' 형태의 '주제'를 추출하는 함수
    fun extractSubject(festival: Festival): String {
        // festival.name 예: "봄꽃 축제 1", "가을 단풍 축제 2" 등
        // 여기서는 "축제"라는 단어 앞부분까지만 추출
        // ex) "봄꽃 축제 1" -> "봄꽃 축제"
        // 간단히 substringBefore("축제") + "축제"로 만들거나, 좀 더 정교하게 처리 가능
        val base = festival.name.substringBefore("축제").trim()
        return if (base.isNotEmpty()) base + "축제" else festival.name
    }

    // 주제 리스트 추출
    val subjectList = remember {
        allFestivals
            .map { extractSubject(it) }
            .distinct() // 중복 주제 제거
            .sorted()
    }

    var selectedSubject by remember { mutableStateOf<String?>(null) }

    // 선택된 주제에 해당하는 축제 리스트
    val subjectFestivals = allFestivals.filter {
        selectedSubject != null && extractSubject(it) == selectedSubject
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "주제별 축제",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        // 주제 목록
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(subjectList) { _, subject ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    onClick = {
                        selectedSubject = subject
                    }
                ) {
                    Text(
                        text = subject,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        // 선택된 주제의 축제 목록 표시
        if (selectedSubject != null) {
            Text(
                text = "[$selectedSubject] 주제의 축제 목록",
                style = MaterialTheme.typography.titleMedium.copy(color = SecondaryColor),
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(subjectFestivals) { festival ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFDDDDDD)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = festival.imageRes),
                                contentDescription = festival.name,
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(end = 8.dp),
                                tint = Color.Unspecified
                            )
                            Column {
                                Text(
                                    text = festival.name,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp)
                                )
                                Text(
                                    text = "장소: ${festival.location}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Text(
                                    text = "기간: ${festival.date}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
