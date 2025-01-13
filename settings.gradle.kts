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
include(":lib:core:api")
include(":lib:core:database")
include(":lib:core:di")
include(":lib:core:presentation")
include(":lib:core:resources")
include(":lib:core:types")

// auth library

//  auth client
include(":lib:auth:shared")
include(":lib:auth:accloss")
include(":lib:auth:picking")

// company library

// company client
include(":lib:company")

// config library

// config client
include(":lib:config")

// user library

// user client
include(":lib:user")

// manager library

// manager client
include(":lib:manager")

// salesman library

// salesman client
include(":lib:salesman")

// product library

// product client
include(":lib:product")

// customer library

// customer client
include(":lib:customer")

// order library

// order client
include(":lib:order")

// document library

// document client
include(":lib:document")
