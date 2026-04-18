plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace = "com.texttetris"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.texttetris"
        minSdk = 26
        versionCode = 1
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    defaultConfig {
        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    dependencies {
        implementation("androidx.core:core-ktx:1.15.0")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
        implementation("androidx.activity:activity-compose:1.9.3")
        implementation(platform("androidx.compose:compose-bom:2024.12.01"))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.foundation:foundation")
        implementation("androidx.compose.material3:material3")
        implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
        implementation("androidx.room:room-runtime:2.6.1")
        implementation("androidx.room:room-ktx:2.6.1")
        ksp("androidx.room:room-compiler:2.6.1")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

        testImplementation("junit:junit:4.13.2")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
        testImplementation("app.cash.turbine:turbine:1.1.0")
        testImplementation("io.mockk:mockk:1.13.13")
        androidTestImplementation("androidx.test.ext:junit:1.2.1")
        androidTestImplementation("androidx.test:runner:1.6.2")
        androidTestImplementation("androidx.test:rules:1.6.1")
        androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.5")
        androidTestImplementation("androidx.compose.ui:ui-test:1.7.5")
        androidTestDebugImplementation("androidx.compose.ui:ui-tooling:1.7.5")
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}
