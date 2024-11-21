/*
 * Copyright © 2024 L13 and/or one of its affiliates. All rights reserved.
 * This file is part of the HAVEN project and contains proprietary information of L13.
 * The contents of this file are confidential and constitute a commercial secret of L13.
 * Unauthorized copying, distribution, or disclosure of this file is strictly prohibited.
 * This code is licensed under a commercial license, and may only be used with the express permission of L13.
 * For more details, refer to your licensing agreement.
 */

package com.l13.haven.convention.plugin.configurations

import com.l13.haven.convention.extension.libsMain
import com.l13.haven.convention.plugin.PluginConfiguration
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.plugins.quality.Pmd
import org.gradle.api.plugins.quality.PmdExtension
import org.gradle.api.plugins.quality.PmdPlugin
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.configure

private const val JAVA_RULES_FILE = "/config/pmd/java-rules.xml"

class PmdPluginConfiguration : PluginConfiguration() {

    override fun applyPlugin(plugins: PluginContainer, project: Project) {
        plugins.apply(PmdPlugin::class.java)
    }

    override fun configureExtensions(extensions: ExtensionContainer, project: Project) {
        extensions.configure<PmdExtension> {
            toolVersion = project.libsMain.findLibrary("pmd-tool").get().get().version!!
            isIgnoreFailures = false
            incrementalAnalysis.set(true)
            maxFailures.set(0)
            isConsoleOutput = true
            rulesMinimumPriority.set(5)
            threads.set(4)
            ruleSetFiles = project.files(
                project.resources.text.fromUri(
                    PmdPluginConfiguration::class.java.getResource(JAVA_RULES_FILE)?.toURI()!!
                )
                    .asFile()
            )
        }
    }

    override fun configureTasks(tasks: TaskContainer, project: Project) {
        tasks.named("pmdMain", Pmd::class.java) {
            reports.html.required.set(true)
            reports.xml.required.set(true)
        }
        tasks.named("pmdTest", Pmd::class.java) {
            reports.html.required.set(true)
            reports.xml.required.set(true)
        }
    }
}
