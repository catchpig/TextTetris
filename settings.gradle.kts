pluginManagement {
    repositories {
        maven { url = uri("https://mirrors.cloud.tencent.com/repository/maven/google") }
        maven { url = uri("https://mirrors.cloud.tencent.com/repository/maven/central") }
        maven { url = uri("https://mirrors.cloud.tencent.com/repository/maven/gradle-plugin") }
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        maven { url = uri("https://mirrors.cloud.tencent.com/repository/maven/google") }
        maven { url = uri("https://mirrors.cloud.tencent.com/repository/maven/central") }
        google()
        mavenCentral()
    }
}

rootProject.name = "TextTetris"
include(":app")
