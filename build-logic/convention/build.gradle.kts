plugins {
    `kotlin-dsl`
}

group = "com.wodud7308.movieinfo.buildlogic"

dependencies {
    compileOnly(libs.bundles.gradle)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "wodud7308.movieinfo.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
    }
}
