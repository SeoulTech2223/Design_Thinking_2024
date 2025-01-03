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
import com.example.app_2223.data.Festival
import com.example.app_2223.data.sampleFestivalList
import com.example.app_2223.ui.theme.SecondaryColor

/**
 * 기간별 화면
 * - 축제 시작 날짜의 "월"을 파싱해 봄/여름/가을/겨울로 분류
 * - 선택된 '시즌'을 바탕으로 축제 목록 표시
 */
@Composable
fun TimeScreen() {
    // 시즌 목록
    val seasons = listOf("전체", "봄(3~5월)", "여름(6~8월)", "가을(9~11월)", "겨울(12~2월)")

    var selectedSeason by remember { mutableStateOf("전체") }

    // 축제 리스트 전부
    val allFestivals = sampleFestivalList

    // 축제의 시작 월(예: "2025-04-10 ~ 2025-04-14" -> 4)
    fun getStartMonth(festival: Festival): Int {
        // date 형식 "YYYY-MM-DD ~ YYYY-MM-DD"
        val startDate = festival.date.split("~").getOrNull(0)?.trim() ?: ""
        val split = startDate.split("-")
        return if (split.size == 3) {
            split[1].toIntOrNull() ?: 0
        } else 0
    }

    // 선택된 시즌에 포함되는지 판단
    fun isFestivalInSeason(festival: Festival, season: String): Boolean {
        val month = getStartMonth(festival)
        return when (season) {
            "전체" -> true
            "봄(3~5월)" -> (month in 3..5)
            "여름(6~8월)" -> (month in 6..8)
            "가을(9~11월)" -> (month in 9..11)
            "겨울(12~2월)" -> (month == 12 || month in 1..2)
            else -> true
        }
    }

    val filteredFestivals = allFestivals.filter { isFestivalInSeason(it, selectedSeason) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "기간별 축제",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        // 시즌 선택
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(seasons) { season ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    onClick = {
                        selectedSeason = season
                    }
                ) {
                    Text(
                        text = season,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        // 선택된 시즌의 축제 목록
        if (selectedSeason != "전체") {
            Text(
                text = "[$selectedSeason] 축제 목록",
                style = MaterialTheme.typography.titleMedium.copy(color = SecondaryColor),
                modifier = Modifier.padding(16.dp)
            )
        } else {
            Text(
                text = "[전체] 축제 목록",
                style = MaterialTheme.typography.titleMedium.copy(color = SecondaryColor),
                modifier = Modifier.padding(16.dp)
            )
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(filteredFestivals) { festival ->
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
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = festival.date,
                                style = MaterialTheme.typography.bodySmall,
                                color = SecondaryColor
                            )
                        }
                    }
                }
            }
        }
    }
}
