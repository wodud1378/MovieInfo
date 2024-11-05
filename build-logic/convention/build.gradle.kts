import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.wodud7308.movieinfo.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.bundles.gradle)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "wodud7308.movieinfo.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "wodud7308.movieinfo.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("hilt") {
            id = "wodud7308.movieinfo.hilt"
            implementationClass = "HiltConventionPlugin"
        }
    }
}
