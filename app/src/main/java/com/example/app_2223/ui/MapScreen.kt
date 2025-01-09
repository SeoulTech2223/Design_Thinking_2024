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
import com.example.app_2223.ui.theme.SurfaceColor

@Composable
fun MapScreen(
    likeStates: MutableMap<String, Pair<Boolean, Int>>,
    onFestivalCardClick: (Festival) -> Unit
) {
    val regions = listOf(
        "서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종",
        "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도",
        "경상북도", "경상남도", "제주도"
    )

    var selectedRegion by remember { mutableStateOf<String?>(null) }

    val regionFestivals = sampleFestivalList.filter {
        selectedRegion != null && it.location.contains(selectedRegion!!, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "지역별 축제",
            style = MaterialTheme.typography.titleLarge.copy(color = SurfaceColor),
            modifier = Modifier.padding(16.dp)
        )

        // 지역 리스트
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(regions) { _, region ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFDDDDDD)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                    onClick = {
                        selectedRegion = region
                    }
                ) {
                    // 예: Row 배치, 아이콘/텍스트 등
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // region 아이콘 쓸거면 Icon() 등 배치
                        // 예시는 단순히 Text만 쓸 경우:
                        Text(
                            text = region,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                        )
                    }
                }
            }
        }


        // 지역 선택 후 해당 축제 목록
        if (selectedRegion != null) {
            Text(
                text = "[$selectedRegion] 지역 축제 목록",
                style = MaterialTheme.typography.titleMedium.copy(color = SurfaceColor),
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(regionFestivals) { festival ->
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
