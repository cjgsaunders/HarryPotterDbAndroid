plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.screenshot)
    alias(libs.plugins.ktlint.gradle)
}

android {
    android {
        packaging {
            resources.excludes.addAll(
                listOf(
                    "META-INF/LICENSE.md",
                    "META-INF/LICENSE-notice.md"
                )
            )
        }

        experimentalProperties["android.experimental.enableScreenshotTest"] = true
        compileSdk = 34
        ksp {
            arg("room.generateKotlin", "true")
        }
        namespace = "com.example.harrypotterapp"
        compileSdk = 34

        testOptions {
            screenshotTests {
                imageDifferenceThreshold = 0.0001f // 0.01%
            }
        }

        defaultConfig {
            applicationId = "com.example.harrypotterapp"
            minSdk = 28
            targetSdk = 34
            versionCode = 1
            versionName = "1.0"

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        kotlinOptions {
            jvmTarget = "11"
        }
        buildFeatures {
            compose = true
        }
    }

    ktlint {
        android = true
    }

    dependencies {

        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.ui.test.junit4)
        androidTestImplementation(libs.mockk)
        androidTestImplementation(libs.truth)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        debugImplementation(libs.androidx.ui.test.manifest)
        debugImplementation(libs.androidx.ui.tooling)
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.compose.android)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.material3)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.androidx.splashscreen)
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.coil.compose)
        implementation(libs.coil.network)
        implementation(libs.compose.runtime)
        implementation(libs.hilt.android)
        implementation(libs.hilt.android)
        implementation(libs.hilt.navigation)
        implementation(libs.kotlin.serialization)
        implementation(libs.okhttp)
        implementation(libs.retrofit)
        implementation(libs.retrofit.converter)
        implementation(libs.room)
        implementation(platform(libs.androidx.compose.bom))
        ksp(libs.hilt.android.compiler)
        ksp(libs.room.compiler)
        screenshotTestImplementation(libs.androidx.compose.ui.tooling)
        testImplementation(libs.kotlinx.coroutines.test)
        testImplementation(libs.turbine)
        testImplementation(libs.junit)
        testImplementation(libs.mockk)
    }
}
