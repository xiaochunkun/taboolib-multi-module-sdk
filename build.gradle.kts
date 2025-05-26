@file:Suppress("PropertyName", "SpellCheckingInspection")

import io.izzel.taboolib.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("io.izzel.taboolib") version "2.0.23" apply false
    kotlin("jvm") version "2.0.21" apply false
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.izzel.taboolib")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    // TabooLib 配置
    // 这里的配置是全局的，如果你的项目有多个模块，这里的配置会被所有模块共享
    // 为了降低理解难度，使用这种更加无脑的配置方式
    configure<TabooLibExtension> {
        description {
            name(rootProject.name)
        }
        env {
            install(Basic, Bukkit, BukkitUtil)
            install(CommandHelper)
            repoTabooLib = "https://maven.mcplugin.top/repository/minecraft/"
        }
        version { taboolib = "6.2.3.2" }
    }

    // 仓库
    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.mcplugin.top/repository/minecraft/")
        }
    }
    // 依赖
    dependencies {
        compileOnly(kotlin("stdlib"))
    }

    // 编译配置
    java {
        withSourcesJar()
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjvm-default=all", "-Xextended-compiler-checks")
        }
    }
}

gradle.buildFinished {
    buildDir.deleteRecursively()
}