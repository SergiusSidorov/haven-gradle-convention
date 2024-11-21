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
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.api.plugins.quality.CheckstylePlugin
import org.gradle.api.tasks.TaskContainer
import org.gradle.kotlin.dsl.configure

private const val CONFIG_FILE = "/config/checkstyle/checkstyle.xml"

class CheckStylePluginConfiguration : PluginConfiguration() {

    override fun applyPlugin(plugins: PluginContainer, project: Project) {
        plugins.apply(CheckstylePlugin::class.java)
    }

    override fun configureExtensions(extensions: ExtensionContainer, project: Project) {
        extensions.configure<CheckstyleExtension> {
            toolVersion = project.libsMain.findLibrary("checkstyle-tool").get().get().version!!
        }
    }

    override fun configureTasks(tasks: TaskContainer, project: Project) {
        tasks.named("checkstyleMain", Checkstyle::class.java) {
            configureCheckstyleTask(this)
        }

        tasks.named("checkstyleTest", Checkstyle::class.java) {
            configureCheckstyleTask(this)
        }
    }

    private fun configureCheckstyleTask(checkstyle: Checkstyle) {
        with(checkstyle) {
            config = project.resources.text.fromUri(
                CheckStylePluginConfiguration::class.java.getResource(CONFIG_FILE)?.toURI()!!
            )

            ignoreFailures = false
            maxErrors = 0
            maxWarnings = 0

            // reports
            reports.xml.required.set(false)
            reports.html.required.set(true)
            reports.sarif.required.set(true)
        }
    }
}