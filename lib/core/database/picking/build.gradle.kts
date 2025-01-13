import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
}

group = "org.closs.core.database"

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    sourceSets {

        androidMain.dependencies {
            // Sqldelight
            implementation(libs.sqldelight.android.driver)
        }

        commonMain.dependencies {
            // Database: shared
            implementation(projects.lib.core.database.shared)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Sqldelight
            implementation(libs.sqldelight.coroutines.extensions)
            implementation(libs.sqldelight.async.extensions)
        }
    }
}

android {
    namespace = "org.closs.core.database"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

sqldelight {
    databases {
        create("ClossDb") {
            packageName.set("org.closs.core.database")
            dialect(libs.sqldelight.sqlite.dialect)
            generateAsync.set(true)
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
            dependency(project(":lib:core:database:shared"))
        }
    }
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
