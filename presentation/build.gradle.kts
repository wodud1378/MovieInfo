plugins {
    alias(libs.plugins.movieinfo.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.wodud7308.presentation"
}


dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
