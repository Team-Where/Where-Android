// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.where.library)
    alias(libs.plugins.where.serialization)
    alias(libs.plugins.where.hilt)
}

android {
    namespace = "com.sooum.data"
}

dependencies {
    implementation(project(":domain"))

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.serialization.converter)
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.datastore.preferences)

    testImplementation(libs.retrofit.serialization.converter)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.1")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")
    testImplementation(kotlin("test"))
}