# Syrup Library

Syrup Library is a Fabric library mod for reusable, typed configuration in Syrup Studios mods.

## Maven coordinates

The current Fabric artifact is published as:

```text
net.syrupstudios:syrup_library-1.20.1-fabric:0.1.0
```

Publish it to the local Maven repository with:

```shell
./gradlew :1.20.1-fabric:publishToMavenLocal
```

Consumers can then add it to a Fabric Loom project:

```kotlin
repositories {
    mavenLocal()
}

dependencies {
    modImplementation("net.syrupstudios:syrup_library-1.20.1-fabric:0.1.0")
}
```

Or to a Maven project:

```xml
<repositories>
    <repository>
        <id>fabric</id>
        <url>https://maven.fabricmc.net/</url>
    </repository>
</repositories>

<dependency>
    <groupId>net.syrupstudios</groupId>
    <artifactId>syrup_library-1.20.1-fabric</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Remote publishing

Set the repository URL and, when required, credentials through environment variables before running `publish`:

```shell
MAVEN_REPOSITORY_URL=https://maven.example.com/releases \
MAVEN_REPOSITORY_USERNAME=your-username \
MAVEN_REPOSITORY_PASSWORD=your-password \
./gradlew :1.20.1-fabric:publish
```

The equivalent Gradle properties are `mavenRepositoryUrl`, `mavenRepositoryUsername`, and
`mavenRepositoryPassword`. Keep credentials in the user-level Gradle properties file, not in this repository.
