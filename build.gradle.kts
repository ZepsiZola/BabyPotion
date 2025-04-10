plugins {
    java
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "me.rime"
version = project.property("version").toString()
repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://jitpack.io/") //GriefPrevention Repository
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:${project.property("paper_version")}")
    //compileOnly("io.papermc.folia:folia-api:${project.property("paper_version")}")
    implementation(kotlin("stdlib-jdk8"))
    compileOnly("com.github.TechFortress:GriefPrevention:17.0.0") //GriefPrevention Dependency
}

tasks {
    assemble {
        dependsOn("shadowJar")
    }
    processResources {
        inputs.properties[version]
        filesMatching("plugin.yml") {
            expand(project.properties)
        }
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    shadowJar {
        project.configurations.implementation.get().isCanBeResolved = true
        configurations = listOf(project.configurations.implementation.get())
        archiveBaseName.set("BabyPotion")
        archiveClassifier.set("shaded")
        archiveVersion.set("")
        minimize()
    }
}

kotlin {
    jvmToolchain(17)
}
