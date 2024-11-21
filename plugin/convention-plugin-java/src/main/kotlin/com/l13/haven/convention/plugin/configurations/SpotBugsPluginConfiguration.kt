/*
 * Copyright © 2024 L13 and/or one of its affiliates. All rights reserved.
 * This file is part of the HAVEN project and contains proprietary information of L13.
 * The contents of this file are confidential and constitute a commercial secret of L13.
 * Unauthorized copying, distribution, or disclosure of this file is strictly prohibited.
 * This code is licensed under a commercial license, and may only be used with the express permission of L13.
 * For more details, refer to your licensing agreement.
 */

package com.l13.haven.convention.plugin.configurations

import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort
import com.github.spotbugs.snom.SpotBugsExtension
import com.github.spotbugs.snom.SpotBugsPlugin
import com.github.spotbugs.snom.SpotBugsTask
import com.l13.haven.convention.extension.libsMain
import com.l13.haven.convention.plugin.PluginConfiguration
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

private const val EXCLUSION_CONFIG_FILE = "/config/spotbugs/exclude.xml"

class SpotBugsPluginConfiguration : PluginConfiguration() {

    override fun applyPlugin(plugins: PluginContainer, project: Project) {
        plugins.apply(SpotBugsPlugin::class.java)

        project.dependencies {
            add(
                "spotbugsPlugins",
                project.libsMain.findLibrary("spotbugs-findsecbugs-plugin").get()
            )
        }
    }

    override fun configureExtensions(extensions: ExtensionContainer, project: Project) {
        extensions.configure<SpotBugsExtension> {
            toolVersion.set(project.libsMain.findLibrary("spotbugs-tool").get().get().version)
        }
    }

    override fun configureTasks(tasks: TaskContainer, project: Project) {
        tasks.named("spotbugsMain", SpotBugsTask::class.java) {
            effort.set(Effort.MORE)

            excludeFilter.set(
                project.resources.text.fromUri(
                    SpotBugsPluginConfiguration::class.java.getResource(EXCLUSION_CONFIG_FILE)?.toURI()!!
                )
                    .asFile()
            )

            ignoreFailures = false
            showStackTraces.set(false)

            reportLevel.set(Confidence.DEFAULT)

            reports.create("html") {
                required.set(true)
            }

            reports.create("sarif") {
                required.set(true)
            }
        }
    }
}