/*
 * Copyright © 2024 L13 and/or one of its affiliates. All rights reserved.
 * This file is part of the HAVEN project and contains proprietary information of L13.
 * The contents of this file are confidential and constitute a commercial secret of L13.
 * Unauthorized copying, distribution, or disclosure of this file is strictly prohibited.
 * This code is licensed under a commercial license, and may only be used with the express permission of L13.
 * For more details, refer to your licensing agreement.
 */
package com.l13.haven.convention.plugin

import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.tasks.TaskContainer

abstract class PluginConfiguration {

    fun apply(project: Project) {
        applyPlugin(project.plugins, project)
        configureExtensions(project.extensions, project)
        configureTasks(project.tasks, project)
    }

    protected abstract fun applyPlugin(plugins: PluginContainer, project: Project)

    protected abstract fun configureExtensions(extensions: ExtensionContainer, project: Project)

    protected abstract fun configureTasks(tasks: TaskContainer, project: Project)
}