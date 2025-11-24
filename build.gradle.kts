/*
 * Copyright (c) 2025 Vadim Shabanov.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile
import org.jetbrains.kotlin.konan.target.HostManager
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform") version "2.2.21"
    `maven-publish`
}

group = "bindings"
version = "0.1.0-SNAPSHOT"

kotlin {
    @Suppress("OPT_IN_USAGE")
    applyDefaultHierarchyTemplate {}

    js {
        binaries.library()
        browser()
        nodejs()
        compilerOptions {
            freeCompilerArgs.add("-Xes-long-as-bigint")
        }
    }
    mingwX64()
    linuxX64()
    linuxArm64()
    macosX64()
    macosArm64()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    tvosX64()
    tvosArm64()
    tvosSimulatorArm64()
    watchosX64()
    watchosArm32()
    watchosArm64()
    watchosSimulatorArm64()
    watchosDeviceArm64()

    targets.withType<KotlinNativeTarget> {
        val main by this.compilations.getting
        val sqlite3 by main.cinterops.creating {
            includeDirs("$projectDir/src/include")
        }
    }

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.experimental.ExperimentalNativeApi")
                optIn("kotlinx.cinterop.ExperimentalForeignApi")
                optIn("kotlinx.cinterop.BetaInteropApi")
            }
        }
        val commonMain by getting {}
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
            }
        }
        val nativeMain by getting {}
        val nativeTest by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.8.1")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(npm("@sqlite.org/sqlite-wasm", "3.50.4-build1"))
                implementation(npm("copy-webpack-plugin", "13.0.1"))
            }
        }
    }
}

tasks.withType<KotlinNativeCompile> {
    compilerOptions {
        freeCompilerArgs.addAll(
            when {
                HostManager.hostIsLinux -> listOf(
                    "-linker-options",
                    "-lsqlite3 -L/usr/lib/x86_64-linux-gnu -L/usr/lib"
                )
                HostManager.hostIsMingw -> listOf(
                    "-linker-options",
                    "-lsqlite3 -Lc:\\msys64\\mingw64\\lib"
                )
                else                    -> listOf(
                    "-linker-options",
                    "-lsqlite3"
                )
            }
        )
    }
}

tasks.withType<KotlinNativeCompile> {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

//--------------------------------------- Publications configuration --------------------------------------

val repoPath = layout.buildDirectory.get().asFile.resolve("repo")

publishing {
    repositories {
        maven {
            name = "dev"
            url = uri(repoPath)
        }
    }
}
