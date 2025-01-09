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

@Composable
fun SubjectScreen(
    likeStates: MutableMap<String, Pair<Boolean, Int>>,
    onFestivalCardClick: (Festival) -> Unit
) {
    val allFestivals = sampleFestivalList

    // 주제 추출 함수
    fun extractSubject(festival: Festival): String {
        val base = festival.name.substringBefore("축제").trim()
        return if (base.isNotEmpty()) base + "축제" else festival.name
    }

    // 주제 목록
    val subjectList = remember {
        allFestivals
            .map { extractSubject(it) }
            .distinct()
            .sorted()
    }

    var selectedSubject by remember { mutableStateOf<String?>(null) }

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
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFDDDDDD)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                    onClick = {
                        selectedSubject = subject
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = subject,
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
                        )
                    }
                }
            }
        }


        // 선택된 주제의 축제 목록
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
