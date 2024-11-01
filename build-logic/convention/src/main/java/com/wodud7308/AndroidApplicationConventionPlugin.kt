import com.android.build.api.dsl.ApplicationExtension
import com.wodud7308.convention.configureKotlinAndroid
import com.wodud7308.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    applicationId = libs.findVersion("appId").get().toString()
                    targetSdk =
                        libs
                            .findVersion("targetSdk")
                            .get()
                            .toString()
                            .toInt()
                    compileSdk =
                        libs
                            .findVersion("compileSdk")
                            .get()
                            .toString()
                            .toInt()
                    minSdk =
                        libs
                            .findVersion("minSdk")
                            .get()
                            .toString()
                            .toInt()
                    versionCode =
                        libs
                            .findVersion("versionCode")
                            .get()
                            .toString()
                            .toInt()
                    versionName = libs.findVersion("versionName").get().toString()
                }

                configureKotlinAndroid(this)
            }
        }
    }
}
