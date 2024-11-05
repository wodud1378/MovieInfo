plugins {
    alias(libs.plugins.movieinfo.android.library)
}

android {
    namespace = "com.wodud7308.domain"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.androidx.paging.common)
}
