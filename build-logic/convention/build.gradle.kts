plugins {
    `kotlin-dsl`
}

group = "com.where.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}


gradlePlugin {
    plugins {
        register("hilt") {
            id = libs.plugins.where.hilt.get().pluginId
            implementationClass = "HiltConventionPlugin"
        }
        register("application") {
            id = libs.plugins.where.application.get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("library") {
            id = libs.plugins.where.library.get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("serialization") {
            id = libs.plugins.where.serialization.get().pluginId
            implementationClass = "SerializationConventionPlugin"
        }
    }
}