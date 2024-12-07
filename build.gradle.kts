import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = "2.0.21"))
    }
}

plugins {
    idea
    kotlin("jvm") version "2.0.21"
}

allprojects {
    group = "io.github.tomplum"
    version = "1.0.0"

    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/tomplum/advent-of-code-libs")
            credentials {
                username = "TomPlum"
                password = System.getenv("GITHUB_PACKAGE_REGISTRY_KEY")
            }
        }
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
        implementation("io.github.tomplum:advent-of-code-libs:2.5.0") {
            exclude("org.slf4j", "slf4j-api")
        }
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_18)
        }
    }
}
