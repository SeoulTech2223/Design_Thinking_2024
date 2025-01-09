package com.example.app_2223.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.app_2223.ui.theme.SurfaceColor

@Composable
fun HomeScreen(
    likeStates: MutableMap<String, Pair<Boolean, Int>>,
    onFestivalCardClick: (Festival) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    // 초기 1회 랜덤 섞기 -> 고정
    val randomFestivals = remember { sampleFestivalList.shuffled() }

    // 검색어 필터
    val filteredFestivals = if (searchQuery.isBlank()) {
        randomFestivals
    } else {
        randomFestivals.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
                    it.location.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // 상단에 "추천 축제" 글자
        Text(
            text = "추천 축제",
            style = MaterialTheme.typography.headlineSmall.copy(color = SurfaceColor),
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, bottom = 4.dp)
        )

        // 검색 필터
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(text = "축제 검색") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        // 축제 목록
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            items(filteredFestivals) { festival ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFDDDDDD)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                    onClick = { onFestivalCardClick(festival) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            painter = painterResource(id = festival.imageRes),
                            contentDescription = festival.name,
                            modifier = Modifier
                                .size(60.dp)
                                .padding(end = 16.dp),
                            tint = Color.Unspecified
                        )
                        Column {
                            Text(
                                text = festival.name,
                                style = MaterialTheme.typography.titleMedium.copy(fontSize = 18.sp)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "장소: ${festival.location}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "기간: ${festival.date}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
