plugins {
    alias(libs.plugins.movieinfo.android.application)
    alias(libs.plugins.movieinfo.hilt)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.wodud7308.movieinfo"
    defaultConfig {
        applicationId = "com.wodud7308.movieinfo"
        versionCode = 1
        versionName = "0.0.1"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(path = ":data"))
    implementation(project(path = ":domain"))
    implementation(project(path = ":presentation"))

    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
