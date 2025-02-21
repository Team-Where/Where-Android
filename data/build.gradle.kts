// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(libs.plugins.where.android.library.get().pluginId)
    id(libs.plugins.where.hilt.get().pluginId)
}

android {
    namespace = "com.sooum.data"
}

dependencies {
    implementation(project(":domain"))
}