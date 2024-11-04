plugins {
    alias(libs.plugins.movieinfo.android.application)
}

android {
    namespace = "com.wodud7308.movieinfo"
    defaultConfig {
        applicationId = "com.wodud7308.movieinfo"
        versionCode = 1
        versionName = "0.0.1"
    }
}

dependencies {
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
