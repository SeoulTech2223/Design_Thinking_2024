package com.example.app_2223

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.app_2223.data.sampleFestivalList
import com.example.app_2223.ui.theme.CustomTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.WriteBatch

class LoginActivity : ComponentActivity() {

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CustomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        /*
                        // 데이터 업로드 버튼
                        Button(onClick = { uploadFestivalsBatch() }) {
                            Text("데이터 업로드")
                        }*/

                        // 로그인 화면
                        LoginScreen(
                            onLoginSuccess = { navigateToMainActivity() },
                            onNavigateToRegister = { navigateToRegister() },
                            onNavigateToResetPassword = { navigateToResetPassword() }
                        )
                    }
                }
            }
        }
    }


    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            uploadFestivalsBatch()
            CustomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        modifier = Modifier.padding(innerPadding),
                        onLoginSuccess = { navigateToMainActivity() },
                        onNavigateToRegister = { navigateToRegister() },
                        onNavigateToResetPassword = { navigateToResetPassword() }
                    )
                }
            }
        }
    }*/
/*
    fun uploadFestivalsBatch() {
        val db = FirebaseFirestore.getInstance()
        val batch = db.batch()

        sampleFestivalList.forEach { festival ->
            val docRef = db.collection("festivals").document(festival.name) // 이름으로 문서 ID 지정

            val festivalData = mapOf(
                "name" to festival.name,
                "location" to festival.location,
                "date" to festival.date,
                "imageUrl" to "https://placeholder-image-url", // Placeholder 이미지 URL
                "likesCount" to 0
            )

            batch.set(docRef, festivalData)
        }

        batch.commit()
            .addOnSuccessListener {
                println("모든 축제가 Firestore에 성공적으로 등록되었습니다!")
                Toast.makeText(this, "데이터 업로드 성공!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                println("배치 작업 실패: ${e.message}")
                Toast.makeText(this, "업로드 실패: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }*/


    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToResetPassword() {
        val intent = Intent(this, ResetPasswordActivity::class.java)
        startActivity(intent)
    }

    @Composable
    fun LoginScreen(
        modifier: Modifier = Modifier,
        onLoginSuccess: () -> Unit,
        onNavigateToRegister: () -> Unit,
        onNavigateToResetPassword: () -> Unit
    ) {
        val context = LocalContext.current

        // 상태값 정의
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "로그인",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .align(Alignment.Start)
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("이메일") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("비밀번호") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        // 이메일 인증 완료 여부도 확인
                                        val user = auth.currentUser
                                        if (user != null && user.isEmailVerified) {
                                            Toast.makeText(context, "로그인 성공", Toast.LENGTH_SHORT)
                                                .show()
                                            onLoginSuccess()
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "이메일 인증이 필요합니다.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "로그인 실패: ${task.exception?.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            Toast.makeText(context, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .align(Alignment.End)
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = "로그인",
                        modifier = Modifier.padding(5.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onNavigateToRegister,
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color.Transparent, // 버튼 배경색 제거
                        contentColor = Color.Black // 텍스트 색 검정
                    ),
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 150.dp)
                ) {
                    Text(
                        text = "회원가입",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold // 텍스트 굵게
                        )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onNavigateToResetPassword,
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = Color.Transparent, // 버튼 배경색 제거
                        contentColor = Color.Black // 텍스트 색 검정
                    ),
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = "비밀번호 초기화",
                        color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold // 텍스트 굵게
                        )
                    )
                }

            }
        }
    }
}
