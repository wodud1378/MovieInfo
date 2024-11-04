package com.wodud7308.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType,
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        val apiKey = gradleLocalProperties(rootDir, providers).getProperty("API_KEY")
        when (extensionType) {
            ExtensionType.Application -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, apiKey)
                        }
                    }
                }
            }

            ExtensionType.Library -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, apiKey)
                        }
                    }
                }
            }
        }
    }
}

private fun BuildType.configureDebugBuildType(apiKey: String) {
    buildConfigField("String", "API_KEY", apiKey)
    buildConfigField("String", "API_BASE_URL", "\"https://api.themoviedb.org/3/\"")
}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    apiKey: String,
) {
    buildConfigField("String", "API_KEY", apiKey)
    buildConfigField("String", "API_BASE_URL", "\"https://api.themoviedb.org/3/\"")

    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
    )
}
