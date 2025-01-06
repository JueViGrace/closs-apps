plugins {
}

subprojects {
    tasks.withType<Jar> {
        val projectName = project.path
            .split(":")
            .filter { it != "lib" && it.isNotEmpty() }
            .joinToString("-")

        archiveBaseName.set(projectName)
    }
}
