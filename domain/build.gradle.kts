// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.where.library)
    alias(libs.plugins.where.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.sooum.domain"
}

dependencies {
    api(libs.coroutine)
    api(libs.kotlinx.serialization.json)
}