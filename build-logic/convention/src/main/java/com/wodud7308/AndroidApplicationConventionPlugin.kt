import com.android.build.api.dsl.ApplicationExtension
import com.wodud7308.convention.ExtensionType
import com.wodud7308.convention.configureBuildTypes
import com.wodud7308.convention.configureKotlinAndroid
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
                    targetSdk = 34
                }

                configureKotlinAndroid(this)
                configureBuildTypes(this, ExtensionType.Application)
            }
        }
    }
}
