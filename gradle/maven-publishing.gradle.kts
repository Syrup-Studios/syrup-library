import org.gradle.api.plugins.BasePluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

val mavenRepositoryUrl = providers.gradleProperty("mavenRepositoryUrl")
    .orElse(providers.environmentVariable("MAVEN_REPOSITORY_URL"))
val mavenRepositoryUsername = providers.gradleProperty("mavenRepositoryUsername")
    .orElse(providers.environmentVariable("MAVEN_REPOSITORY_USERNAME"))
val mavenRepositoryPassword = providers.gradleProperty("mavenRepositoryPassword")
    .orElse(providers.environmentVariable("MAVEN_REPOSITORY_PASSWORD"))
val archiveName = extensions.getByType<BasePluginExtension>().archivesName.get()
val javaComponent = components["java"]

extensions.configure<PublishingExtension> {
    publications {
        create<MavenPublication>("mavenJava") {
            from(javaComponent)
            artifactId = archiveName

            pom {
                name.set("${project.property("mod.name")} ($archiveName)")
                description.set(project.property("mod.description") as String)
                url.set("https://github.com/Syrup-Studios/syrup-library")

                licenses {
                    license {
                        name.set(project.property("mod.license") as String)
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("SodaSyrup")
                        name.set(project.property("mod.authors") as String)
                        organization.set("Syrup Studios")
                        organizationUrl.set("https://github.com/Syrup-Studios")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/Syrup-Studios/syrup-library.git")
                    developerConnection.set("scm:git:ssh://git@github.com/Syrup-Studios/syrup-library.git")
                    url.set("https://github.com/Syrup-Studios/syrup-library")
                }
                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/Syrup-Studios/syrup-library/issues")
                }
            }
        }
    }

    repositories {
        if (mavenRepositoryUrl.isPresent) {
            maven {
                name = "remote"
                url = uri(mavenRepositoryUrl.get())
                if (mavenRepositoryUsername.isPresent || mavenRepositoryPassword.isPresent) {
                    credentials {
                        username = mavenRepositoryUsername.orNull
                        password = mavenRepositoryPassword.orNull
                    }
                }
            }
        }
    }
}
