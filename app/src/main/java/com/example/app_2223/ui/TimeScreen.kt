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
import com.example.app_2223.ui.theme.SecondaryColor

@Composable
fun TimeScreen(
    likeStates: MutableMap<String, Pair<Boolean, Int>>,
    onFestivalCardClick: (Festival) -> Unit
) {
    // "전체"를 포함한 시즌 목록
    val seasons = listOf("전체", "봄(3~5월)", "여름(6~8월)", "가을(9~11월)", "겨울(12~2월)")

    // 아직 아무 시즌도 클릭 안 했을 때는 null
    var selectedSeason by remember { mutableStateOf<String?>(null) }

    val allFestivals = sampleFestivalList

    fun getStartMonth(festival: Festival): Int {
        val startDate = festival.date.split("~").getOrNull(0)?.trim() ?: ""
        val split = startDate.split("-")
        return if (split.size == 3) {
            split[1].toIntOrNull() ?: 0
        } else 0
    }

    fun isFestivalInSeason(festival: Festival, season: String): Boolean {
        val month = getStartMonth(festival)
        return when (season) {
            "전체" -> true
            "봄(3~5월)" -> (month in 3..5)
            "여름(6~8월)" -> (month in 6..8)
            "가을(9~11월)" -> (month in 9..11)
            "겨울(12~2월)" -> (month == 12 || month in 1..2)
            else -> false
        }
    }

    // selectedSeason이 null이면 빈 리스트, 그렇지 않으면 필터
    val filteredFestivals = remember(selectedSeason) {
        if (selectedSeason == null) emptyList()
        else allFestivals.filter { isFestivalInSeason(it, selectedSeason!!) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "기간별 축제",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        // 시즌 선택 목록
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(seasons) { season ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFDDDDDD)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                    onClick = {
                        selectedSeason = season
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = season,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                        )
                    }
                }
            }
        }

        // 선택된 시즌이 없으면 안내만 표시, 있으면 실제 축제 목록 표시
        if (selectedSeason == null) {
            Text(
                text = "",
                style = MaterialTheme.typography.titleMedium.copy(color = SecondaryColor),
                modifier = Modifier.padding(16.dp)
            )
        } else {
            val label = "[$selectedSeason] 축제 목록"
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium.copy(color = SecondaryColor),
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(filteredFestivals) { festival ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFDDDDDD)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        onClick = { onFestivalCardClick(festival) }
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
}
