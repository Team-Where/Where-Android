import com.android.build.api.dsl.ApplicationExtension
import com.where.buildlogic.configureKotlinAndroid
import com.where.buildlogic.configureProguard
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.application")
            apply(plugin = "org.jetbrains.kotlin.android")

            extensions.configure<ApplicationExtension> {

                defaultConfig.targetSdk = 34

                configureKotlinAndroid(this)
                configureProguard(this)
            }
        }
    }
}