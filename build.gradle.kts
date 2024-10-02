import pl.allegro.tech.build.axion.release.domain.hooks.HookContext

plugins {
    `version-catalog`
    `maven-publish`
    alias(libsMain.plugins.axion.release.plugin)
}

group = "com.haven.convention"
project.version = scmVersion.version

subprojects {
    println("* " + project.name)

    if (project.childProjects.isEmpty()) {
        println("  --> apply common project settings...")

        project.version = rootProject.version

        var groupSuffix = ""

        if (project.parent?.name == "catalog") {
            groupSuffix = ".catalog"
        }

        group = "com.haven.convention$groupSuffix"

        apply(plugin = "maven-publish")

        configure<PublishingExtension> {
            repositories {
                maven {
                    name = "GitHubPackages"
                    url = uri("https://maven.pkg.github.com/SergiusSidorov/haven-gradle-convention")
                    credentials {
                        username = project.findProperty("publish.username") as String? ?: System.getenv("USERNAME")
                        password = project.findProperty("publish.token") as String? ?: System.getenv("TOKEN")
                    }
                }
            }
        }

    } else {
        println("  --> skipping group project...")
    }
}

scmVersion {
    localOnly.set(false)
    useHighestVersion.set(true)

    tag {
        prefix.set("v")
        initialVersion { _, _ -> "0.0.0" }
        releaseOnlyOnReleaseBranches = true
    }

    repository {
        type.set("git")
    }

    nextVersion {
        suffix.set("RC")
        separator.set("-")
    }

    checks {
        uncommittedChanges.set(true)
        aheadOfRemote.set(true)
        snapshotDependencies.set(true)
    }

    hooks {
        // workaround, will be replaced with preRelease hook and fileUpdate
        pre(
            "fileUpdate", mapOf(
                "encoding" to "utf-8",
                "file" to file("README.md"),
                "pattern" to KotlinClosure2({ previousVersion: String, _: HookContext -> "v$previousVersion" }),
                "replacement" to KotlinClosure2({ currentVersion: String, _: HookContext -> "v$currentVersion" })
            )
        )

        pre("commit")
    }
}
