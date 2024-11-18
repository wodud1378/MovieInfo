plugins {
    alias(libs.plugins.movieinfo.android.feature)
    alias(libs.plugins.movieinfo.hilt)
}

android {
    namespace = "com.wodud7308.movieinfo.feature.favorites"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
