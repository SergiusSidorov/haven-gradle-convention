plugins {
    `version-catalog`
}

catalog {
    versionCatalog {
        from(files("libs-main.versions.toml"))
    }
}

publishing {
    publications {
        create<MavenPublication>("versionCatalog") {
            from(components["versionCatalog"])
        }
    }
}
