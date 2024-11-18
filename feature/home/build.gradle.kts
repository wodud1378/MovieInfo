plugins {
    alias(libs.plugins.movieinfo.android.feature)
    alias(libs.plugins.movieinfo.hilt)
}

android {
    namespace = "com.wodud7308.movieinfo.feature.home"
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.androidx.paging)
    implementation(libs.glide)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
