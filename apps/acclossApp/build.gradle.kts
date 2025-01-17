import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

group = "org.closs.accloss"

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.core.ktx)

            // App update
            implementation(libs.androidx.app.update)
            implementation(libs.androidx.app.update.ktx)

            // Koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
        }

        commonMain.dependencies {
            // Types
            implementation(projects.lib.core.types.shared)
            implementation(projects.lib.core.types.accloss)

            // Api: client
            implementation(projects.lib.core.api.shared)
            implementation(projects.lib.core.api.accloss)

            // Database
            implementation(projects.lib.core.database.shared)
            implementation(projects.lib.core.database.accloss)

            // Di: client
            implementation(projects.lib.core.di)

            // Presentation: client
            implementation(projects.lib.core.presentation.shared)
            implementation(projects.lib.core.presentation.accloss)

            // Resources: client
            implementation(projects.lib.core.resources)

            // App: shared
            implementation(projects.lib.app)

            // Auth
            implementation(projects.lib.auth.accloss)
            implementation(projects.lib.auth.shared)

            // User
            implementation(projects.lib.user)

            // Product
            implementation(projects.lib.product)

            // Order
            implementation(projects.lib.order.shared)
            implementation(projects.lib.order.accloss)

            // Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // ViewModel
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel.savedstate)
            implementation(libs.androidx.lifecycle.runtime.compose)

            // Navigation
            implementation(libs.androidx.navigation.compose)

            // Kotlinx
            implementation(libs.kotlinx.coroutines.core)

            // Ktor: client
            implementation(libs.ktor.client.core)

            // Konnection
            implementation(libs.konnection)

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.common)

            // Coroutines
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "org.closs.accloss"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

    defaultConfig {
        applicationId = "org.closs.accloss"
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
        targetSdk =
            libs.versions.android.targetSdk
                .get()
                .toInt()
        versionCode = 1
        versionName =
            libs.versions.acclossApp.version
                .get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose {
    desktop {
        application {
            mainClass = "org.closs.accloss.ApplicationKt"

            nativeDistributions {
                targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                packageName = "org.closs.accloss"
                packageVersion =
                    libs.versions.acclossApp.version
                        .get()
            }
        }
    }

    resources {
        generateResClass = never
    }
}
