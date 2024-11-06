plugins {
    `version-catalog`
    alias(libsMain.plugins.axion.release.plugin)
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

project.version = scmVersion.version

allprojects {
    project.version = rootProject.version
}
