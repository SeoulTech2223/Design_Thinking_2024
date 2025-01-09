plugins {
    // Android Gradle Plugin, Kotlin Android
    id("com.android.application")
    kotlin("android")

    // 여기서 Google Services Plugin 적용
    // 버전은 상위 pluginManagement에서 이미 지정해뒀으므로 version 생략
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.app_2223"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.app_2223"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Compose, Material, Accompanist 등
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.1-alpha")
    implementation(libs.androidx.navigation.compose)

    // (버전 카탈로그가 있다면 alias 형태 사용, 여기서는 예시로 직접 기재)
    // implementation(libs.androidx.lifecycle.runtime.ktx)
    // implementation(platform(libs.androidx.compose.bom))
    // ...

    // Firebase BOM 및 각 기능별 라이브러리
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-database")
    implementation(libs.core.ktx)
}
