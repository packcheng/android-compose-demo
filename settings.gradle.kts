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
include(":app_news")
include(":app_snack")
include(":app_owl")
include(":app_crane")
include(":app_reply")
include(":app_jetlagged")
include(":app_jetcaster")
include(":app_sunflower")
include(":app_todo")
include(":app_nowinandroid")
