buildscript {
    repositories {
        gradlePluginPortal()
        maven(url = "https://jitpack.io")
    }
}

plugins {
    kotlin("jvm") version "1.6.21"
    id("maven-publish")
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

allprojects {
    group = "ru.grishankov.network.ktor"
    version = "1.0.0"

    repositories {
        mavenLocal()
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.21")
    implementation("io.ktor:ktor-client-core:2.1.0")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(kotlin.sourceSets.getByName("main").kotlin.srcDirs)
}

publishing {
    publications {
        create<MavenPublication>("ktor-network-adapter") {
            artifactId = "ktor-network-adapter"
            group = "ru.grishankov.network.ktor"
            version = "1.0.0"
            pom.packaging = "jar"
            artifact(sourcesJar.get())
        }
    }
}