import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

group = "org.closs.order"

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    sourceSets {
        androidMain.dependencies {
            // Koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
        }

        commonMain.dependencies {
            // Types
            implementation(projects.lib.core.types.shared)
            implementation(projects.lib.core.types.picking)

            // Database: client
            implementation(projects.lib.core.database.shared)
            implementation(projects.lib.core.database.picking)

            // Api: client
            implementation(projects.lib.core.api.shared)
            implementation(projects.lib.core.api.picking)

            // Presentation: client
            implementation(projects.lib.core.presentation.shared)
            implementation(projects.lib.core.presentation.picking)

            // Order
            implementation(projects.lib.order.shared)

            // Compose Resources
            implementation(projects.lib.core.resources)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.viewmodel.savedstate)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.compose)

            // Coil
            implementation(libs.coil.compose)
            implementation(libs.coil.core)
            implementation(libs.coil.ktor3)
            implementation(libs.coil.cache)

            // Coroutines
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
    }
}

android {
    namespace = "org.closs.order"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

    defaultConfig {
        minSdk =
            libs.versions.android.minSdk
                .get()
                .toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

compose.resources {
    generateResClass = never
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
