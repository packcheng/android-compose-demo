//
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}

// 使用CATALOGS管理依赖
enableFeaturePreview("VERSION_CATALOGS")

rootProject.name = "ComposeDemo"
//include (":app")
include(":app_survey")
include(":app_chat")
