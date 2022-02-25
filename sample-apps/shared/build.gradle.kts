/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("dev.icerock.moko.kswift")
}

val mokoResourcesVersion = "0.18.0"
val mokoMvvmVersion = "0.12.0"
val dependenciesList = listOf(
    "dev.icerock.moko:mvvm-core:$mokoMvvmVersion",
    "dev.icerock.moko:mvvm-livedata:$mokoMvvmVersion",
    "dev.icerock.moko:mvvm-state:$mokoMvvmVersion",
    "dev.icerock.moko:mvvm-livedata-resources:$mokoMvvmVersion",
    "dev.icerock.moko:resources:$mokoResourcesVersion"
)

kotlin {
    android()

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { target ->
        target.binaries.framework {
            baseName = "shared"

            xcf.add(this)

            dependenciesList.forEach { export(it) }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                dependenciesList.forEach { api(it) }
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}

afterEvaluate {
    tasks.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFrameworkTask>()
        .forEach { xcFrameworkTask ->
            val syncName: String = xcFrameworkTask.name.replace("assemble", "sync")

            tasks.create(syncName, Sync::class) {
                this.group = "xcode"

                this.from(File(xcFrameworkTask.outputDir, xcFrameworkTask.buildType.getName()))
                this.into(File(project.buildDir, "xcode"))

                this.dependsOn(xcFrameworkTask)
            }
        }
}

kswift {
    install(dev.icerock.moko.kswift.plugin.feature.SealedToSwiftEnumFeature)
    install(dev.icerock.moko.kswift.plugin.feature.PlatformExtensionFunctionsFeature)
}
