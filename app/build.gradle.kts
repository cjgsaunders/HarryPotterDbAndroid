plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    alias(libs.plugins.screenshot)
    //alias(libs.plugins.hilt.android)
}

android {
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

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.hilt.android)
    implementation(libs.androidx.lifecycle.runtime.compose.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.hilt.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.okhttp)
    implementation(libs.hilt.navigation)
    implementation(libs.kotlin.serialization)
    implementation(libs.retrofit)
    implementation(libs.room)
    ksp(libs.room.compiler)
    implementation(libs.compose.runtime)
    implementation(libs.retrofit.converter)
    implementation(platform(libs.androidx.compose.bom))
    testImplementation(libs.junit)
    screenshotTestImplementation(libs.androidx.compose.ui.tooling)

    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.androidx.ui.tooling)


}