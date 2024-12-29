package com.example.app_2223

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.app_2223.ui.theme.CustomTheme

class ResetPasswordActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ResetPasswordScreen(
                        modifier = Modifier.padding(innerPadding),
                        onResetSuccess = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
fun ResetPasswordScreen(
    modifier: Modifier = Modifier,
    onResetSuccess: () -> Unit
) {
    val context = LocalContext.current

    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "비밀번호 재설정 화면",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("새 비밀번호") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = confirmNewPassword,
            onValueChange = { confirmNewPassword = it },
            label = { Text("새 비밀번호 확인") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if (newPassword.isNotEmpty() && confirmNewPassword.isNotEmpty()) {
                    if (newPassword == confirmNewPassword) {
                        // SharedPreferences 에 새 비밀번호로 업데이트
                        val sharedPreferences = context.getSharedPreferences("my_prefs", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("password", newPassword)
                        editor.apply()

                        println("비밀번호 재설정 성공")
                        onResetSuccess()
                    } else {
                        println("새 비밀번호가 일치하지 않습니다.")
                    }
                } else {
                    println("모든 항목을 입력해주세요.")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("비밀번호 재설정")
        }
    }
}