package com.example.app_2223

import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import com.example.app_2223.ui.theme.CustomTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class ResetPasswordActivity : ComponentActivity() {

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

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
    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "비밀번호 재설정",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.Start)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // (1) 이메일 입력
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("이메일") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // (2) 비밀번호 재설정 이메일 전송 버튼
        Button(
            onClick = {
                if (email.isNotEmpty()) {
                    auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "비밀번호 재설정 이메일을 보냈습니다.\n메일함을 확인해주세요.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                onResetSuccess()
                            } else {
                                val exception = task.exception
                                if (exception is FirebaseAuthInvalidUserException) {
                                    // 존재하지 않는 이메일인 경우
                                    Toast.makeText(
                                        context,
                                        "존재하지 않는 이메일입니다. 이메일을 확인하세요.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    // 기타 오류
                                    Toast.makeText(
                                        context,
                                        "이메일 전송 실패: ${exception?.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                } else {
                    Toast.makeText(context, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("비밀번호 재설정 이메일 보내기")
        }
    }
}
