plugins {
    `version-catalog`
}

catalog {
    versionCatalog {
        from(files("../../gradle/versions/libs-test.versions.toml"))
    }
}

publishing {
    publications {
        create<MavenPublication>("versionCatalog") {
            from(components["versionCatalog"])
        }
    }
}
