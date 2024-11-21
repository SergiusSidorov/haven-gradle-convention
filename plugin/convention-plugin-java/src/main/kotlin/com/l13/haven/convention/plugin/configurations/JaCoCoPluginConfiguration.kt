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
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.tasks.TaskContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.gradle.testing.jacoco.plugins.JacocoPluginExtension
import org.gradle.testing.jacoco.tasks.JacocoReport

class JaCoCoPluginConfiguration : PluginConfiguration() {

    override fun applyPlugin(plugins: PluginContainer, project: Project) {
        plugins.apply(JacocoPlugin::class.java)
    }

    override fun configureExtensions(extensions: ExtensionContainer, project: Project) {
        extensions.configure<JacocoPluginExtension> {
            toolVersion = project.libsMain.findLibrary("jacoco-tool").get().get().version!!
        }
    }

    override fun configureTasks(tasks: TaskContainer, project: Project) {
        tasks.named(JavaPlugin.TEST_TASK_NAME, Test::class.java) {
            finalizedBy(tasks.findByName("jacocoTestReport"))
        }
        tasks.named("jacocoTestReport", JacocoReport::class.java) {
            dependsOn(tasks.named(JavaPlugin.TEST_TASK_NAME))

            reports {
                html.required.set(true)
                xml.required.set(true)
            }
        }
    }
}
