plugins {
    alias(libs.plugins.movieinfo.android.library)
    alias(libs.plugins.movieinfo.hilt)
}

android {
    namespace = "com.wodud7308.movieinfo.core.domain"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.androidx.paging.common)
}
