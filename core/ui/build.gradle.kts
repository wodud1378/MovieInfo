plugins {
    alias(libs.plugins.movieinfo.android.library)
    alias(libs.plugins.movieinfo.hilt)
}

android {
    namespace = "com.wodud7308.movieinfo.core.ui"

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.androidx.paging)
    implementation(libs.androidx.cardview)
    implementation(libs.glide)
    implementation(libs.androidx.splash)

    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.androidx.constraintlayout)
    api(libs.androidx.fragment.ktx)
    api(libs.material)
    api(libs.facebook.shimmer)
    api(libs.bundles.lifecycle)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
