import com.android.build.api.dsl.LibraryExtension
import com.wodud7308.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("movieinfo.android.library")
                apply("movieinfo.hilt")
                apply("androidx.navigation.safeargs.kotlin")
            }

            extensions.configure<LibraryExtension> {
                buildFeatures.viewBinding = true
            }

            dependencies {
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:navigation"))
                add("implementation", project(":core:domain"))
            }
        }
    }
}
