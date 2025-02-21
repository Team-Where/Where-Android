// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(libs.plugins.where.android.application.get().pluginId)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    id(libs.plugins.where.hilt.get().pluginId)
}

android {

    defaultConfig {
        applicationId = "com.sooum.where_android"
        versionCode = 1
        versionName = "1.0.0"
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    namespace = "com.sooum.where_android"
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    //Hilt
    implementation(libs.hilt.navigation.compose)

    //Compose
    val composeBom = "androidx.compose:compose-bom:2025.02.00"
    implementation(platform(composeBom))
    androidTestImplementation(platform(composeBom))

    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.navigation:navigation-compose:2.8.7")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")


    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
}