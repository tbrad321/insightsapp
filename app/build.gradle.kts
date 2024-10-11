plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.insights" // Replace with your package name
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.insights"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    compileSdk = 35 // Update this if needed

    defaultConfig {
        applicationId = "com.example.insights"
        minSdk = 21 // Adjust based on your requirements
        targetSdk = 35 // Update this if needed
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/metadata/*.kotlin_metadata"
        }
    }

    kotlinOptions {
        jvmTarget = "1.8" // Or adjust to your needs
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Add any additional libraries you might need
    implementation(libs.junit)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.vision.internal.vkp)
    implementation(libs.material)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    val composeBom = platform("androidx.compose:compose-bom:2024.09.03")
    implementation(composeBom)
    testImplementation(composeBom)
    androidTestImplementation(composeBom)

    // Specify Compose library dependencies without a version definition
    implementation("androidx.compose.foundation:foundation")
    // ..
    testImplementation("androidx.compose.ui:ui-test-junit4")
    // ..
    androidTestImplementation("androidx.compose.ui:ui-test")
    // Add other dependencies based on your previous config
    implementation(libs.androidx.lifecycle.runtime.ktx) // This should match your libs.versions.toml
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
  }


