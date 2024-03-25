// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    val compose_ui_version by extra("1.2.0")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.android.gradlePlugin)
        classpath(libs.hilt.gradlePlugin)
        classpath(libs.kotlin.gradlePlugin)
    }
}

subprojects {
    repositories {
        google()
        mavenCentral()
    }
}