/*
 * Copyright © 2024 L13 and/or one of its affiliates. All rights reserved.
 * This file is part of the HAVEN project and contains proprietary information of L13.
 * The contents of this file are confidential and constitute a commercial secret of L13.
 * Unauthorized copying, distribution, or disclosure of this file is strictly prohibited.
 * This code is licensed under a commercial license, and may only be used with the express permission of L13.
 * For more details, refer to your licensing agreement.
 */

package com.l13.haven.convention.plugin.configurations

import com.l13.haven.convention.plugin.PluginConfiguration
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.configure
import org.owasp.dependencycheck.gradle.DependencyCheckPlugin
import org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension

private const val SUPPRESSION_FILE = "config/oss/suppressions.xml"

class DependencyCheckPluginConfiguration : PluginConfiguration() {

    override fun applyPlugin(plugins: PluginContainer, project: Project) {
        plugins.apply(DependencyCheckPlugin::class.java)
    }

    override fun configureExtensions(extensions: ExtensionContainer, project: Project) {
        extensions.configure<DependencyCheckExtension> {
            autoUpdate = true
            analyzedTypes = listOf("jar", "zip", "war")
            formats = listOf("HTML", "SARIF")
            failOnError = true
            skipTestGroups = true
            scanBuildEnv = true
            scanDependencies = true
            scanConfigurations = listOf() // all configurations
            suppressionFile = SUPPRESSION_FILE

            with(analyzers) {
                experimentalEnabled = true
                assemblyEnabled = false
            }

            with(nvd) {
                apiKey = "74ae5ba5-340c-479e-838f-819fbd0641d4"
                validForHours = 168 // one week
                maxRetryCount = 3
                delay = 10000 // 10 sec
            }
        }
    }

    override fun configureTasks(tasks: TaskContainer, project: Project) {
        tasks.named("check") {
            dependsOn("dependencyCheckAnalyze")
        }
    }
}