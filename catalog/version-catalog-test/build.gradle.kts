plugins {
    `version-catalog`
}

catalog {
    versionCatalog {
        from(files("libs-test.versions.toml"))
    }
}

publishing {
    publications {
        create<MavenPublication>("versionCatalog") {
            from(components["versionCatalog"])
        }
    }
}
