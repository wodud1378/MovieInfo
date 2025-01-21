plugins {
    alias(libs.plugins.movieinfo.android.library)
    alias(libs.plugins.movieinfo.hilt)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.wodud7308.movieinfo.core.data"
}

dependencies {

    implementation(projects.core.domain)
    implementation(libs.androidx.paging)
    implementation(libs.bundles.network)
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
