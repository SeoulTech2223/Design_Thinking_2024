plugins {
    // 버전 카탈로그 예시 (libs.plugins.android.application 등)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    // (굳이 여기서 com.google.gms.google-services 를 선언하지 않아도 됨)
}

// 여기서는 repositories { ... }를 쓰지 않음
// => settings.gradle.kts의 dependencyResolutionManagement가 전역 저장소를 관리
