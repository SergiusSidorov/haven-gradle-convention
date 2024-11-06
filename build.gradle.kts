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

    } else {
        println("  --> skipping group project...")
    }
}

scmVersion {
    localOnly.set(true)
    useHighestVersion.set(true)

    tag {
        prefix.set("v")
        initialVersion { _, _ -> "1.0.0" }
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
        pre("commit")
    }
}
