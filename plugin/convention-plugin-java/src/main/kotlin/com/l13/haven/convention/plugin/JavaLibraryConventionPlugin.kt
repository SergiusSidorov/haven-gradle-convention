/*
 * Copyright © 2024 L13 and/or one of its affiliates. All rights reserved.
 * This file is part of the HAVEN project and contains proprietary information of L13.
 * The contents of this file are confidential and constitute a commercial secret of L13.
 * Unauthorized copying, distribution, or disclosure of this file is strictly prohibited.
 * This code is licensed under a commercial license, and may only be used with the express permission of L13.
 * For more details, refer to your licensing agreement.
 */
package com.l13.haven.convention.plugin

import com.l13.haven.convention.plugin.configurations.CheckStylePluginConfiguration
import com.l13.haven.convention.plugin.configurations.DependencyCheckPluginConfiguration
import com.l13.haven.convention.plugin.configurations.JaCoCoPluginConfiguration
import com.l13.haven.convention.plugin.configurations.JavaPluginConfiguration
import com.l13.haven.convention.plugin.configurations.PmdPluginConfiguration
import com.l13.haven.convention.plugin.configurations.SpotBugsPluginConfiguration
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaLibraryPlugin

class JavaLibraryConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        listOf(
            JavaPluginConfiguration(),
            CheckStylePluginConfiguration(),
            SpotBugsPluginConfiguration(),
            PmdPluginConfiguration(),
            JaCoCoPluginConfiguration(),
            DependencyCheckPluginConfiguration()
        )
            .forEach { pluginConfiguration ->
                pluginConfiguration.apply(project)
            }

        with(project.plugins) {
            apply(BasePlugin::class.java)
            apply(JavaLibraryPlugin::class.java)
        }
    }
}
