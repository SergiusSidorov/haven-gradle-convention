plugins {
    `version-catalog`
}

catalog {
    versionCatalog {
        from(files("../../gradle/versions/libs-main.versions.toml"))
    }
}

publishing {
    publications {
        create<MavenPublication>("versionCatalog") {
            from(components["versionCatalog"])
        }
    }
}
