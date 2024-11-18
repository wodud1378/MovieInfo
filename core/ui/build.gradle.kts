plugins {
    alias(libs.plugins.movieinfo.android.library)
    alias(libs.plugins.movieinfo.hilt)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.wodud7308.movieinfo.core.presentation"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(projects.core.domain)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.paging)
    implementation(libs.material)
    implementation(libs.glide)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.splash)
    implementation(libs.androidx.fragment.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
