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
import com.example.app_2223.data.sampleFestivalList
import com.example.app_2223.ui.theme.SecondaryColor
import com.example.app_2223.ui.theme.SurfaceColor

@Composable
fun MapScreen() {
    // 실제 지도 API나 이미지 로딩, 위치 표시 로직이 필요하지만,
    // 여기서는 간단히 지역 나열 및 클릭 시 축제 목록을 보여주는 예시를 작성합니다.

    // 지역명 예시
    val regions = listOf(
        "서울", "부산", "대구", "인천", "광주", "대전", "울산", "세종",
        "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도",
        "경상북도", "경상남도", "제주도"
    )

    // 선택된 지역
    var selectedRegion by remember { mutableStateOf<String?>(null) }

    // 해당 지역 축제만 필터링
    val regionFestivals = sampleFestivalList.filter {
        selectedRegion != null && it.location.contains(selectedRegion!!, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "대한민국 지도 (베타)",
            style = MaterialTheme.typography.titleLarge.copy(color = SurfaceColor),
            modifier = Modifier.padding(16.dp)
        )

        // 간단히 지역을 리스트로 표시
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(regions) { _, region ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    onClick = {
                        selectedRegion = region
                    }
                ) {
                    Text(
                        text = region,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        // 선택된 지역의 축제 목록
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
}
