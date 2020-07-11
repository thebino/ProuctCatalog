// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.3.72"))
        classpath("com.android.tools.build:gradle:4.2.0-alpha04")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0")

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")

        classpath("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.7.3")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:9.2.1")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
