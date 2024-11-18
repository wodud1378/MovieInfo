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
    implementation(projects.feature.home)
    implementation(projects.feature.search)
    implementation(projects.feature.favorites)
    implementation(projects.feature.profile)

    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.navigation)
    implementation(projects.core.ui)

    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.activity)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
