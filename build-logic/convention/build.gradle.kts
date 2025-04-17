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
    }
}