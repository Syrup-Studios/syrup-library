# Syrup Library

Syrup Library provides reusable, typed configuration for Syrup Studios mods. Its shared source is
structured for Fabric, Forge, and NeoForge builds.

Shared code accesses loader-specific services through `Platform.INSTANCE`. Implementations for
Fabric, Forge, and NeoForge are selected at build time by Stonecutter, so loader APIs stay out of
the shared configuration code.

## Maven coordinates

The current Fabric artifact is published as:

```text
net.syrupstudios:syrup_library:0.1.0+1.20.1-fabric
```

Publish it to the local Maven repository with:

```shell
./gradlew :1.20.1-fabric:publishToMavenLocal
```

Consumers can then add it to a Fabric Loom project:

```kotlin
repositories {
    maven("https://maven.syrupstudios.net/releases/")
}

dependencies {
    modImplementation("net.syrupstudios:syrup_library:0.1.0+1.20.1-fabric")
}
```

Or to a Maven project:

```xml
<repositories>
    <repository>
        <id>syrup-studios</id>
        <url>https://maven.syrupstudios.net/releases/</url>
    </repository>
    <repository>
        <id>fabric</id>
        <url>https://maven.fabricmc.net/</url>
    </repository>
</repositories>

<dependency>
    <groupId>net.syrupstudios</groupId>
    <artifactId>syrup_library</artifactId>
    <version>0.1.0+1.20.1-fabric</version>
</dependency>
```

## Remote publishing

The remote repository defaults to `https://maven.syrupstudios.net/releases/`. Set its credentials
through environment variables before running `publish`:

```shell
MAVEN_REPOSITORY_USERNAME=your-username \
MAVEN_REPOSITORY_PASSWORD=your-password \
./gradlew :1.20.1-fabric:publish
```

The equivalent Gradle properties are `mavenRepositoryUsername` and `mavenRepositoryPassword`.
You can override the repository with `MAVEN_REPOSITORY_URL` or `mavenRepositoryUrl`. Keep
credentials in the user-level Gradle properties file, not in this repository.
