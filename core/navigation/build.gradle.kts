plugins {
    alias(libs.plugins.movieinfo.android.library)
    alias(libs.plugins.movieinfo.hilt)
}

android {
    namespace = "com.wodud7308.movieinfo.core.navigation"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
