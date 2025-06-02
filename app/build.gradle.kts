plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp.gradlePlugin)          // Apply KSP plugin
    alias(libs.plugins.hilt.android.gradlePlugin) // Apply Hilt plugin
    alias(libs.plugins.kotlin.serialization)      // Apply @Serializable
}

android {
    namespace = "learningprogramming.academy.reviewrabbit"
    compileSdk = 35

    defaultConfig {
        applicationId = "learningprogramming.academy.reviewrabbit"
        minSdk = 26
        targetSdk = 35
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    //Viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    //Navigation
    implementation(libs.androidx.navigation.compose)
    //Retrofit
    implementation (libs.retrofit)
    // For OkHttp logging interceptor
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    //Coil
    implementation(libs.coil.compose)
    //Google Font - Inter
    implementation(libs.androidx.ui.text.google.fonts)
    // Extended Icons
    implementation(libs.androidx.material.icons.extended)
    // Hilt Dependencies
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)
    // Kotlin Serialization library
    implementation(libs.kotlinx.serialization.json)
    // Retrofit with Kotlin Serialization integration
    implementation(libs.retrofit.kotlinx.serialization.converter.jakewharton)
    //Prettytime
    implementation ("org.ocpsoft.prettytime:prettytime:5.0.8.Final")
    //PreferencesDataStore
    implementation(libs.datastore.preferences)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}