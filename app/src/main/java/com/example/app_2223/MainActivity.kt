package com.example.app_2223

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.app_2223.ui.theme.BackgroundColor
import com.example.app_2223.ui.theme.CustomTheme
import com.example.app_2223.ui.theme.SecondaryColor
import com.example.app_2223.ui.theme.SurfaceColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 커스텀 테마 적용 (예: 다크모드/컬러 등)
            CustomTheme {
                MainScreenWithBottomNav()
            }
        }
    }
}

/**
 * 메인 스크린 (하단 네비게이션 + NavHost)
 */
@Composable
fun MainScreenWithBottomNav() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            // 아래에서 정의한 BottomNavigationBar
            BottomNavigationBar(navController = navController)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        // 네비게이션 호스트 (화면 전환 담당)
        NavHost(
            navController = navController,
            startDestination = "home",  // 초기 탭
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen() }
            composable("map") { MapScreen() }
            composable("subject") { SubjectScreen() }
            composable("time") { TimeScreen() }
            composable("mypage") { MyPageScreen() }
        }
    }
}

/**
 * 하단 네비게이션 바
 * Navigation Compose를 사용하므로, NavHostController를 파라미터로 받음
 */
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    // 바텀 탭에 표시될 항목 정의 (label, route, iconRes)
    val navItems = listOf(
        NavItem("홈",      "home",    R.drawable.ic_home),
        NavItem("지역별",  "map",     R.drawable.ic_map),
        NavItem("주제별",  "subject", R.drawable.ic_subject),
        NavItem("기간별",  "time",    R.drawable.ic_time),
        NavItem("마이페이지", "mypage", R.drawable.ic_mypage)
    )

    // 현재 백스택(네비게이션 상태)을 관찰하여, 현재 선택된 탭(route)을 알아낸다
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // 하단 바를 둥근 모서리 + 테두리 + 배경색으로 감싸기
    Surface(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        border = BorderStroke(0.3.dp, Color(0xFFBBBBBB)),
        color = MaterialTheme.colorScheme.background
    ) {
        NavigationBar(
            containerColor = BackgroundColor,
            contentColor = SurfaceColor
        ) {
            // 각 탭을 순회하면서 NavigationBarItem 생성
            navItems.forEach { item ->
                val isSelected = (currentRoute == item.route)

                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        // 이미 선택된 route가 아니라면 이동
                        if (!isSelected) {
                            navController.navigate(item.route) {
                                // 뒤로가기로 인해 다시 홈으로 돌아가지 않도록 설정
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // 동일한 화면 여러 개 쌓이지 않도록
                                launchSingleTop = true
                                // 이전에 방문한 상태도 복구
                                restoreState = true
                            }
                        }
                    },
                    icon = {
                        // PNG 아이콘
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
                            modifier = Modifier
                                .padding(1.dp)
                        )
                    },
                    // 아이콘 tint는 직접 지정했으므로 Color.Unspecified
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

/** BottomNavigationBar 아이템 정보를 담을 데이터 클래스 */
data class NavItem(
    val label: String,
    val route: String,
    val iconRes: Int
)

/**
 * 아래는 각 탭에 해당하는 Composable 화면 예시
 * 실제 구현할 내용(레이아웃/기능)은 TODO 주석으로 남겨두시되,
 * 'TODO(...) 함수'를 사용하면 크래시가 발생하므로 꼭 제거해주세요.
 */

@Composable
fun HomeScreen() {
    // 간단한 예시 UI만 두고, 이후 실제 구현할 부분은 주석으로 남기세요
    Text(text = "홈 화면 UI가 여기에 들어갈 예정입니다.")
    // TODO: Implement HomeScreen UI here.
    // (위처럼 주석만 남겨두면 됩니다.)
}

@Composable
fun MapScreen() {
    Text(text = "지도 화면 UI가 여기에 들어갈 예정입니다.")
    // TODO: Implement MapScreen UI here.
}

@Composable
fun SubjectScreen() {
    Text(text = "주제별 화면 UI가 여기에 들어갈 예정입니다.")
    // TODO: Implement SubjectScreen UI here.
}

@Composable
fun TimeScreen() {
    Text(text = "기간별 화면 UI가 여기에 들어갈 예정입니다.")
    // TODO: Implement TimeScreen UI here.
}

@Composable
fun MyPageScreen() {
    Text(text = "마이페이지 화면 UI가 여기에 들어갈 예정입니다.")
    // TODO: Implement MyPageScreen UI here.
}