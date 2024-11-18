plugins {
    alias(libs.plugins.movieinfo.android.feature)
    alias(libs.plugins.movieinfo.hilt)
}

android {
    namespace = "com.wodud7308.movieinfo.feature.search"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
