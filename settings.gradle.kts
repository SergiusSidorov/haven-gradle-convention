import kotlin.io.path.Path
import kotlin.io.path.isDirectory
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name

rootProject.name = "haven-convention"

Path("catalog").listDirectoryEntries()
    .forEach { dir ->
        if (dir.isDirectory()) {
            include(":catalog:${dir.name}")
        }
    }

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
