plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(project(":plugin:convention-plugin-common"))

    implementation(toDependency(libsMain.plugins.spotbugs.plugin))
    implementation(toDependency(libsMain.plugins.dependencycheck.plugin))
}

fun toDependency(provider: Provider<PluginDependency>): String {
    val id = provider.get().pluginId
    val version = provider.get().version
    return "${id}:${id}.gradle.plugin:${version}"
}

gradlePlugin {
    plugins {
        register("JavaLibraryConventionPlugin") {
            displayName = "Convention Plugin for Java Library"
            id = "com.l13.haven.convention.plugin.java-library"
            implementationClass = "com.l13.haven.convention.plugin.JavaLibraryConventionPlugin"
            version = project.version
        }
    }
}
