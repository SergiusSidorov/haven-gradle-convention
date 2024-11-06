rootProject.name = "Haven Gradle Convention Plugins"

dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    versionCatalogs {
        create("libsMain") {
            from(files("gradle/versions/libs-main.versions.toml"))
        }

        create("libsTest") {
            from(files("gradle/versions/libs-test.versions.toml"))
        }
    }
}
