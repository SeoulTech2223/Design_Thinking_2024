package com.example.app_2223.data

import com.example.app_2223.R

/**
 * 축제 데이터 클래스
 */
data class Festival(
    val name: String,
    val location: String,
    val date: String,
    val imageRes: Int // Drawable 리소스 ID
)

/**
 * 샘플 축제 리스트 (실제로는 서버/DB에서 받아올 수 있음)
 * 30개 정도 예시
 */
val sampleFestivalList = listOf(
    Festival("봄꽃 축제 1", "서울", "2025-04-10 ~ 2025-04-14", R.drawable.festival_placeholder),
    Festival("봄꽃 축제 2", "부산", "2025-04-11 ~ 2025-04-15", R.drawable.festival_placeholder),
    Festival("봄꽃 축제 3", "인천", "2025-04-12 ~ 2025-04-16", R.drawable.festival_placeholder),
    Festival("여름 바다 축제 1", "부산", "2025-07-01 ~ 2025-07-05", R.drawable.festival_placeholder),
    Festival("여름 바다 축제 2", "강원도", "2025-07-02 ~ 2025-07-06", R.drawable.festival_placeholder),
    Festival("여름 바다 축제 3", "제주도", "2025-07-03 ~ 2025-07-07", R.drawable.festival_placeholder),
    Festival("가을 단풍 축제 1", "강원도", "2025-09-21 ~ 2025-09-25", R.drawable.festival_placeholder),
    Festival("가을 단풍 축제 2", "대구", "2025-09-22 ~ 2025-09-26", R.drawable.festival_placeholder),
    Festival("가을 단풍 축제 3", "대전", "2025-09-23 ~ 2025-09-27", R.drawable.festival_placeholder),
    Festival("겨울 눈꽃 축제 1", "대구", "2025-12-01 ~ 2025-12-05", R.drawable.festival_placeholder),
    Festival("겨울 눈꽃 축제 2", "서울", "2025-12-02 ~ 2025-12-06", R.drawable.festival_placeholder),
    Festival("겨울 눈꽃 축제 3", "경기도", "2025-12-03 ~ 2025-12-07", R.drawable.festival_placeholder),
    Festival("한강 불꽃 축제 1", "서울", "2025-10-02 ~ 2025-10-02", R.drawable.festival_placeholder),
    Festival("한강 불꽃 축제 2", "서울", "2025-10-03 ~ 2025-10-03", R.drawable.festival_placeholder),
    Festival("벚꽃 축제 1", "경기도", "2025-04-05 ~ 2025-04-09", R.drawable.festival_placeholder),
    Festival("벚꽃 축제 2", "부산", "2025-04-06 ~ 2025-04-10", R.drawable.festival_placeholder),
    Festival("벚꽃 축제 3", "광주", "2025-04-07 ~ 2025-04-11", R.drawable.festival_placeholder),
    Festival("사과 축제 1", "청송", "2025-10-15 ~ 2025-10-20", R.drawable.festival_placeholder),
    Festival("사과 축제 2", "충주", "2025-10-16 ~ 2025-10-21", R.drawable.festival_placeholder),
    Festival("사과 축제 3", "영주", "2025-10-17 ~ 2025-10-22", R.drawable.festival_placeholder),
    Festival("와인 축제 1", "영동", "2025-09-10 ~ 2025-09-12", R.drawable.festival_placeholder),
    Festival("맥주 축제 1", "서울", "2025-08-01 ~ 2025-08-02", R.drawable.festival_placeholder),
    Festival("막걸리 축제 1", "전주", "2025-05-10 ~ 2025-05-12", R.drawable.festival_placeholder),
    Festival("커피 축제 1", "강릉", "2025-10-08 ~ 2025-10-09", R.drawable.festival_placeholder),
    Festival("얼음 축제 1", "화천", "2025-01-05 ~ 2025-01-10", R.drawable.festival_placeholder),
    Festival("얼음 축제 2", "인제", "2025-01-06 ~ 2025-01-11", R.drawable.festival_placeholder),
    Festival("호수 축제 1", "춘천", "2025-08-15 ~ 2025-08-17", R.drawable.festival_placeholder),
    Festival("단오 축제 1", "강릉", "2025-06-01 ~ 2025-06-05", R.drawable.festival_placeholder),
    Festival("고추 축제 1", "청양", "2025-09-01 ~ 2025-09-05", R.drawable.festival_placeholder),
    Festival("한우 축제 1", "횡성", "2025-10-25 ~ 2025-10-29", R.drawable.festival_placeholder),
    Festival("우포늪 축제 1", "경남 창녕", "2025-04-20 ~ 2025-04-23", R.drawable.festival_placeholder),
)
