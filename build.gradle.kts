import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.tasks.BintrayUploadTask

plugins {
    kotlin("multiplatform") version "1.3.50"
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
}

group = "dev.nies"
version = "0.0.1"

repositories {
    mavenCentral()
}

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */
    jvm()

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

bintray {
    val bintrayUser: String by project
    val bintrayApiKey: String by project
    user = bintrayUser
    key = bintrayApiKey

    publish = false

    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        name = "CraftKt"
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

tasks {
    val bintrayUpload by existing(BintrayUploadTask::class) {
        doFirst {
            //            val publications = publishing.publications.map {
//                it.name
//            }
//
//            publications.forEach { println(it) }
//
//            setPublications(publishing.publications.map {
//                it.name
//            })
            setPublications("jvm")
        }
    }
}