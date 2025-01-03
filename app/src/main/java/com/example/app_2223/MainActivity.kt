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
import com.example.app_2223.navigation.NavItem
import com.example.app_2223.ui.*
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

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
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

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    // 바텀 탭에 표시될 항목 (label, route, iconRes)
    val navItems = listOf(
        NavItem("홈",      "home",    R.drawable.ic_home),
        NavItem("지역별",  "map",     R.drawable.ic_map),
        NavItem("주제별",  "subject", R.drawable.ic_subject),
        NavItem("기간별",  "time",    R.drawable.ic_time),
        NavItem("마이페이지", "mypage", R.drawable.ic_mypage)
    )

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
