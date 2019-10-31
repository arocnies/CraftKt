import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask
import org.gradle.api.publish.maven.MavenPom

plugins {
    kotlin("multiplatform") version "1.3.50"
    `maven-publish`
    `java-library`
    id("com.github.johnrengelman.shadow") version "5.1.0"
    id("com.jfrog.bintray") version "1.8.4"
}

group = "dev.nies"
version = "0.0.1"

repositories {
    mavenCentral()
}

kotlin {
    /*  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */
    jvm()

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                api(kotlin("stdlib-jdk8"))
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
    }
}

/*
 @see https://kotlinexpertise.com/kotlinlibrarydistibution/
 By default, the generated POM does not list the library dependencies, which is done by mapping each compile dependency
 to a valid dependency XML node.
 */
@Suppress("DEPRECATION")
fun MavenPom.addDependencies() = withXml {
    asNode().appendNode("dependencies").let { depNode ->
        configurations.compile.allDependencies.forEach {
            depNode.appendNode("dependency").apply {
                appendNode("groupId", it.group)
                appendNode("artifactId", it.name)
                appendNode("version", it.version)
            }
        }
    }
}


val craftktArtifactId = "craftkt"

val shadowJar: ShadowJar by tasks
shadowJar.apply {
    baseName = craftktArtifactId
    classifier = null
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = craftktArtifactId
//            from(components["java"])
            artifact(shadowJar)
            pom.addDependencies()
        }
    }
}

bintray {
    val bintrayUser: String by project
    val bintrayApiKey: String by project
    user = bintrayUser
    key = bintrayApiKey

    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        name = craftktArtifactId
        repo = "CraftKt"
        userOrg = "nies"
        websiteUrl = "https://nies.dev"
        githubRepo = "arocnies/CraftKt"
        vcsUrl = "https://github.com/arocnies/CraftKt"
        description = "A library for performing iterative and incremental transformations."
        setLabels("kotlin")
        setLicenses("MIT")
        version(delegateClosureOf<BintrayExtension.VersionConfig> {
            name = project.version.toString()
        })
    })
}

@Suppress("UNUSED_VARIABLE")
tasks {
    val bintrayUpload by existing(BintrayUploadTask::class) {
        dependsOn("publishToMavenLocal")
        doFirst {
            val publications = publishing.publications.map {
                it.name
            }.toTypedArray()
            setPublications(*publications)
        }
    }
}