rootProject.name = "closs-apps"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(":apps:acclossApp")
include(":apps:pickingApp")
include(":lib")

// core client library

// network library
include(":lib:core:api")

// database library
include(":lib:core:database:shared")
include(":lib:core:database:picking")
include(":lib:core:database:accloss")

// dependency injection library
include(":lib:core:di")

// presentation library
include(":lib:core:presentation:shared")
include(":lib:core:presentation:picking")
include(":lib:core:presentation:accloss")

// compose resources library
include(":lib:core:resources")

// types library
include(":lib:core:types:shared")
include(":lib:core:types:picking")
include(":lib:core:types:accloss")

// app library
include(":lib:app")

// auth library
include(":lib:auth:shared")
include(":lib:auth:accloss")
include(":lib:auth:picking")

// config library
include(":lib:config")

// user library
include(":lib:user")

// salesman library
include(":lib:salesman")

// product library
include(":lib:product")

// customer library
include(":lib:customer")

// order library
include(":lib:order")

// document library
include(":lib:document")
