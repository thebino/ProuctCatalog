import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("io.gitlab.arturbosch.detekt")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "de.stuermerbenjamin.productcatalog"
        // API 21 | 5.0 ignore multidex
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0.0"

        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = false
        viewBinding = true
        dataBinding = true
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }

    buildTypes {
        named("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
            isMinifyEnabled = false
            isTestCoverageEnabled = true
        }

        named("release") {
            isDebuggable = false
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-common-java8:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.2.0")

    // arch
    testImplementation("androidx.arch.core:core-testing:2.1.0")

    // workmanager
    implementation("androidx.work:work-runtime-ktx:2.3.4")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0")
    testImplementation("androidx.navigation:navigation-testing:2.3.0")
    debugImplementation("androidx.fragment:fragment-testing:1.2.5")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.4")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.3")

    // Layout
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.core:core-ktx:1.3.0")

    // Design
    implementation("com.google.android.material:material:1.1.0")
    implementation("androidx.palette:palette-ktx:1.0.0")

    // Room
    val roomVersion = "2.2.5"
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    testImplementation("androidx.room:room-testing:$roomVersion")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:2.1.2")
    testImplementation("androidx.paging:paging-common:2.1.2")

    // persistence
    implementation("com.google.code.gson:gson:2.8.5")

    // leak canary
    debugImplementation("com.squareup.leakcanary:leakcanary-android:1.6.3")
    releaseImplementation("com.squareup.leakcanary:leakcanary-android-no-op:1.6.3")

    // Crashlytics
    implementation("com.crashlytics.sdk.android:crashlytics:2.10.1")

    // image loading
    implementation("io.coil-kt:coil:0.9.5")

    // Testing
    androidTestUtil("androidx.test:orchestrator:1.2.0")
    testImplementation("junit:junit:4.12")
    testImplementation("androidx.test:core:1.2.0")
    testImplementation("androidx.test:runner:1.2.0")
    testImplementation("androidx.test.ext:junit:1.1.1")
    testImplementation("androidx.test.ext:truth:1.2.0")
    testImplementation("androidx.test.espresso:espresso-core:3.2.0")
    testImplementation("androidx.test.espresso:espresso-intents:3.2.0")
    implementation("androidx.test.espresso:espresso-idling-resource:3.3.0-rc01")
    testImplementation("org.robolectric:robolectric:4.3.1")

    androidTestImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
    androidTestImplementation("androidx.test:rules:1.2.0")
    androidTestImplementation("androidx.test:runner:1.2.0")
}
