pluginManagement {
    repositories {
        // Gradle 플러그인을 받을 저장소
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    // 여기서 Google Services 플러그인 버전을 지정해 줍니다.
    plugins {
        id("com.google.gms.google-services") version "4.4.2"
    }
}

dependencyResolutionManagement {
    // 여기는 프로젝트 전역의 의존성(라이브러리) 저장소
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "2223"
include(":app")
