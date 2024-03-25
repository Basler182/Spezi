pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Spezi"
include(":app")
include(":spezi-feature:login")
include(":spezi-feature:contact")
include(":core:designsystem")
include(":spezi-feature:chart")
include(":core:datastore")
include(":core:notification")
include(":core:bluetooth-connectivity")
include(":spezi-feature:onboarding")