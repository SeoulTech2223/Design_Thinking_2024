package com.example.app_2223

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.app_2223.ui.theme.CustomTheme

class ResetPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ResetPasswordScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ResetPasswordScreen(modifier: Modifier = Modifier) {
    Text(
        text = "비밀번호 재설정 화면",
        modifier = modifier
    )
}