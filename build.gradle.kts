plugins {
    java
    id("com.gradleup.shadow") version "9.0.1"
}

subprojects {
    plugins.apply("java")
    plugins.apply("com.gradleup.shadow")

    group = rootProject.group
    version = rootProject.version

    repositories {
        mavenCentral()
    }

    dependencies {
        // General Dependencies
        compileOnly("org.projectlombok:lombok:${rootProject.property("lombok_version")}")

        annotationProcessor("org.projectlombok:lombok:${rootProject.property("lombok_version")}")
        implementation("org.javatuples:javatuples:${rootProject.property("javatuples_version")}")

        // Minestom Dependencies
        implementation("net.minestom:minestom:${rootProject.property("minestom_version")}")

        implementation("team.unnamed:creative-api:${rootProject.property("creative_version")}")
        implementation("team.unnamed:creative-serializer-minecraft:${rootProject.property("creative_version")}")
        implementation("team.unnamed:creative-server:${rootProject.property("creative_version")}")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21

        withJavadocJar()
        withSourcesJar()

        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }

        sourceSets.main {
            java.srcDir("src/main/java")
        }
    }

    tasks.withType<Jar> {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }

    tasks.withType<Wrapper> {
        gradleVersion = rootProject.gradle.gradleVersion
    }

    tasks.getByName("build").dependsOn("shadowJar")
}