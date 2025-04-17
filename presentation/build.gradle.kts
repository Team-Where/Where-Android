// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.where.application)
    alias(libs.plugins.where.serialization)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.where.hilt)
    alias(libs.plugins.google.gms.google.services)
}

android {

    defaultConfig {
        applicationId = "com.where.android"
        versionCode = 1
        versionName = "1.0.0"
    }

    buildFeatures {
        viewBinding = true
    }
    namespace = "com.sooum.where_android"
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.hilt.navigation.compose)

    //Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.fragment:fragment-compose:1.8.5")
    implementation("androidx.compose.ui:ui-viewbinding:1.7.8")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.navigation:navigation-compose:2.8.7")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    implementation("androidx.navigation:navigation-fragment-ktx:2.8.8")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.8")
    
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
//    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.performance)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.kotlinx.datetime)

    implementation("com.kakao.sdk:v2-share:2.21.0") // 카카오톡 공유 API 모듈
}