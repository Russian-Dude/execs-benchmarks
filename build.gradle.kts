import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.6"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.7.21"
}

group = "com.rdude"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.6")

    implementation("com.github.Russian-Dude:exECS:1.5.0")
}

buildscript {
    repositories {
        maven("https://jitpack.io")
    }
    dependencies {
        classpath("com.github.Russian-Dude:execs-plugin:1.5.1-1")
    }
}

tasks.test {
    useJUnitPlatform()
}

apply(plugin = "execs-plugin")

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}


benchmark {
    targets {
        register("main")
    }
}