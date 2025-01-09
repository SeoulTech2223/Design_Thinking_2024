package com.example.app_2223

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app_2223.navigation.NavItem
import com.example.app_2223.data.Festival
import com.example.app_2223.data.sampleFestivalList
import com.example.app_2223.ui.*
import com.example.app_2223.ui.FestivalDetailScreen
import com.example.app_2223.ui.LikedFestivalsScreen
import com.example.app_2223.ui.theme.BackgroundColor
import com.example.app_2223.ui.theme.CustomTheme
import com.example.app_2223.ui.theme.SecondaryColor
import com.example.app_2223.ui.theme.SurfaceColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 커스텀 테마 적용
            CustomTheme {
                MainScreenWithBottomNav()
            }
        }
    }
}

@Composable
fun MainScreenWithBottomNav() {
    val navController = rememberNavController()

    /**
     * 좋아요 상태 저장: key = 축제이름, value = Pair<isLiked, likeCount>
     * 간단히 remember로 관리 (실제론 ViewModel / Repository 권장)
     */
    val likeStates = remember {
        mutableStateMapOf<String, Pair<Boolean, Int>>().apply {
            sampleFestivalList.forEach { fest ->
                this[fest.name] = Pair(false, (10..50).random()) // 임의 좋아요 수
            }
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        // NavHost 설정
        NavHost(
            navController = navController,
            startDestination = "home",  // 초기 탭 (홈 탭)
            modifier = Modifier.padding(innerPadding)
        ) {
            // Home
            composable("home") {
                HomeScreen(
                    //festivals = sampleFestivalList,
                    likeStates = likeStates,
                    onFestivalCardClick = { festival ->
                        navController.navigate("detail/${festival.name}")
                    }
                )
            }
            // 지역별
            composable("map") {
                MapScreen(
                    //festivals = sampleFestivalList,
                    likeStates = likeStates,
                    onFestivalCardClick = { festival ->
                        navController.navigate("detail/${festival.name}")
                    }
                )
            }
            // 주제별
            composable("subject") {
                SubjectScreen(
                    //festivals = sampleFestivalList,
                    likeStates = likeStates,
                    onFestivalCardClick = { festival ->
                        navController.navigate("detail/${festival.name}")
                    }
                )
            }
            // 기간별
            composable("time") {
                TimeScreen(
                    //festivals = sampleFestivalList,
                    likeStates = likeStates,
                    onFestivalCardClick = { festival ->
                        navController.navigate("detail/${festival.name}")
                    }
                )
            }
            // 마이페이지
            composable("mypage") {
                MyPageScreen(
                    onNavigateToLikedFestivals = {
                        navController.navigate("liked")
                    }
                )
            }
            // 추가1) 축제 상세화면: 라우트 파라미터로 축제이름을 받음
            composable(
                route = "detail/{festivalName}",
                arguments = listOf(navArgument("festivalName") { type = NavType.StringType })
            ) { backStackEntry ->
                val festivalName = backStackEntry.arguments?.getString("festivalName") ?: ""
                // 축제 목록에서 festivalName에 해당하는 데이터 찾기
                val festival = sampleFestivalList.find { it.name == festivalName }
                if (festival == null) {
                    // 없는 축제면 에러 처리 (간단히 Text로 표시)
                    Text(text = "축제를 찾을 수 없습니다.")
                } else {
                    // likeStates에서 현재 like 상태/수 가져오기
                    val (isLiked, likeCount) = likeStates[festival.name] ?: (false to 0)

                    FestivalDetailScreen(
                        festival = festival,
                        isLiked = isLiked,
                        likeCount = likeCount,
                        onToggleLike = { fest ->
                            val current = likeStates[fest.name]
                            if (current != null) {
                                val newState = if (current.first) {
                                    // 좋아요 취소
                                    current.copy(first = false, second = current.second - 1)
                                } else {
                                    // 좋아요 누름
                                    current.copy(first = true, second = current.second + 1)
                                }
                                likeStates[fest.name] = newState
                            }
                        },
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }
            }
            // 추가2) 내가 좋아요 누른 축제 목록 화면
            composable("liked") {
                LikedFestivalsScreen(
                    festivals = sampleFestivalList,
                    likeStates = likeStates,
                    onFestivalClick = { fest ->
                        // 축제 상세로 이동
                        navController.navigate("detail/${fest.name}")
                    },
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    // 바텀 탭에 표시될 항목
    val navItems = listOf(
        NavItem("홈",      "home",    R.drawable.ic_home),
        NavItem("지역별",  "map",     R.drawable.ic_map),
        NavItem("주제별",  "subject", R.drawable.ic_subject),
        NavItem("기간별",  "time",    R.drawable.ic_time),
        NavItem("마이페이지", "mypage", R.drawable.ic_mypage)
    )

    // 현재 라우트 체크
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Surface(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        border = BorderStroke(0.3.dp, Color(0xFFBBBBBB)),
        color = MaterialTheme.colorScheme.background
    ) {
        NavigationBar(
            containerColor = BackgroundColor,
            contentColor = SurfaceColor
        ) {
            navItems.forEach { item ->
                val isSelected = (currentRoute == item.route)
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            navController.navigate(item.route) {
                                // 첫 화면(Home)으로 돌아가는 루트 등, 상태 복원 세팅
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconRes),
                            contentDescription = item.label,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(1.dp),
                            tint = if (isSelected) SurfaceColor else SecondaryColor
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(1.dp)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Unspecified,
                        unselectedIconColor = Color.Unspecified,
                        selectedTextColor = SurfaceColor,
                        unselectedTextColor = SecondaryColor,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
