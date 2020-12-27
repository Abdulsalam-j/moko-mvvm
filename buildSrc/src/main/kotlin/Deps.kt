object Deps {
    private const val kotlinVersion = "1.4.30-M1"

    private const val androidAppCompatVersion = "1.2.0"
    private const val materialDesignVersion = "1.2.1"
    private const val androidLifecycleVersion = "2.2.0"
    private const val androidCoreTestingVersion = "2.1.0"

    private const val detektVersion = "1.14.0"

    private const val coroutinesVersion = "1.4.2"
    private const val mokoResourcesVersion = "0.13.3"
    const val mokoMvvmVersion = "0.8.2"

    object Android {
        const val compileSdk = 29
        const val targetSdk = 29
        const val minSdk = 16
    }

    object Plugins {
        val androidApplication = GradlePlugin(id = "com.android.application")
        val androidLibrary = GradlePlugin(id = "com.android.library")
        val kotlinJvm = GradlePlugin(id = "org.jetbrains.kotlin.jvm")
        val kotlinMultiplatform = GradlePlugin(id = "org.jetbrains.kotlin.multiplatform")
        val kotlinKapt = GradlePlugin(id = "kotlin-kapt")
        val kotlinAndroid = GradlePlugin(id = "kotlin-android")
        val kotlinParcelize = GradlePlugin(id = "kotlin-parcelize")
        val kotlinSerialization = GradlePlugin(id = "kotlin-serialization")
        val mavenPublish = GradlePlugin(id = "org.gradle.maven-publish")

        val mobileMultiplatform = GradlePlugin(id = "dev.icerock.mobile.multiplatform")
        val iosFramework = GradlePlugin(id = "dev.icerock.mobile.multiplatform.ios-framework")

        val mokoResources = GradlePlugin(
            id = "dev.icerock.mobile.multiplatform-resources",
            module = "dev.icerock.moko:resources-generator:$mokoResourcesVersion"
        )

        val detekt = GradlePlugin(
            id = "io.gitlab.arturbosch.detekt",
            version = detektVersion
        )
    }

    object Libs {
        object Android {
            const val appCompat = "androidx.appcompat:appcompat:$androidAppCompatVersion"
            const val material = "com.google.android.material:material:$materialDesignVersion"
            const val lifecycle = "androidx.lifecycle:lifecycle-extensions:$androidLifecycleVersion"
            const val coroutines =
                "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
        }

        object MultiPlatform {
            const val coroutines =
                "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
            val mokoResources = "dev.icerock.moko:resources:$mokoResourcesVersion"
                .defaultMPL(ios = true)
            const val mokoMvvm = "dev.icerock.moko:mvvm:$mokoMvvmVersion"
        }

        object Tests {
            const val kotlinTestJUnit =
                "org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion"
            const val androidCoreTesting =
                "androidx.arch.core:core-testing:$androidCoreTestingVersion"
        }
    }
}
