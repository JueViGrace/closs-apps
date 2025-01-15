import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

group = "org.closs.picking"

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    sourceSets {
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
            implementation(projects.lib.core.types.picking)

            // Api: client
            implementation(projects.lib.core.api.shared)
            implementation(projects.lib.core.api.picking)

            // Database
            implementation(projects.lib.core.database.shared)
            implementation(projects.lib.core.database.picking)

            // Di: client
            implementation(projects.lib.core.di)

            // Presentation: client
            implementation(projects.lib.core.presentation.shared)
            implementation(projects.lib.core.presentation.picking)

            // Resources: client
            implementation(projects.lib.core.resources)

            // App
            implementation(projects.lib.app)

            // Auth
            implementation(projects.lib.auth.shared)
            implementation(projects.lib.auth.picking)

            // Product
            implementation(projects.lib.product)

            // Order
            implementation(projects.lib.order)

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

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
        }
    }
}

android {
    namespace = "org.closs.picking"
    compileSdk =
        libs.versions.android.compileSdk
            .get()
            .toInt()

    defaultConfig {
        applicationId = "org.closs.picking"
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
            libs.versions.pickingApp.version
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
    resources {
        generateResClass = never
    }
}
