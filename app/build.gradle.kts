plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlin-kapt")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.sparshchadha.expensetracker"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.sparshchadha.expensetracker"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.compose.material3.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // region sdp and ssp
    implementation (libs.sdp.android)
    implementation (libs.ssp.android)
    // endregion

    // region Chucker
    debugImplementation(libs.chucker.library)
    releaseImplementation(libs.chucker.library.no.op)
    // endregion

    // region lottie
    implementation(libs.lottie.compose)
    // endregion

    // region compose collectAsStateWithLifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)
    // endregion

    // region Preference Datastore
    implementation(libs.androidx.datastore.preferences)
    // endregion

    // region date and time picker
    implementation (libs.datetime)
    // endregion

    // region coil
    implementation(libs.coil.compose)
    // endregion

    // region Dagger - Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    kapt(libs.androidx.hilt.compiler)
    // endregion

    // region fragment navigation
    implementation(libs.fragment.navigation)
    // endregion
}