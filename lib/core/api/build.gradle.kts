import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinx.serialization)
}

group = "org.closs.core.api"

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    jvm()

    sourceSets {
        androidMain.dependencies {
        }

        commonMain.dependencies {
            // Types: client
            implementation(projects.lib.core.types)

            // Kotlin Datetime
            implementation(libs.kotlinx.datetime)

            // Serialization
            implementation(libs.kotlinx.serialization.json)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Ktor: client
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.serialization.kotlinx.json)
        }

        jvmMain.dependencies {
            // Coroutines
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "org.closs.core.api"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
