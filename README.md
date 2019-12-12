A library for performing iterative and incremental transformations.

Similar to annotation processing, elements are processed like an `AssemblyLine`.
Steps in the assembly line are called `processors`. 

Processors perform their work and may mark the element to be sent back through the
assembly line.

## Setup
See: [Creating a personal access token for the command line](https://help.github.com/en/github/authenticating-to-github/creating-a-personal-access-token-for-the-command-line)

**Gradle (Kotlin DSL)**
```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/arocnies/CraftKt")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("GPR_USER")
            password = project.findProperty("gpr.key") as String? ?: System.getenv("GPR_API_KEY")
        }
    }
}

dependencies {
    implementation("dev.nies:CraftKt-jvm:VERSION")
}
```

**Gradle (Groovy DSL)**
```groovy
repositories { /* ... */ }

dependencies {
    implementation 'dev.nies:CraftKt-jvm:VERSION'
}
```

**Maven**
```xml
<!-- See https://help.github.com/en/github/managing-packages-with-github-package-registry/configuring-apache-maven-for-use-with-github-package-registry -->
<dependency>
  <groupId>dev.nies</groupId>
  <artifactId>CraftKt-jvm</artifactId>
  <version>VERSION</version>
</dependency>
```