plugins {
    id("java")
    application
    id("com.diffplug.spotless") version "8.2.1"
}

group = "io.huangsam"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("io.huangsam.Main")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.beam:beam-sdks-java-core:2.71.0")
    implementation("org.apache.beam:beam-runners-direct-java:2.71.0")
    implementation("ch.qos.logback:logback-classic:1.5.26")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.hamcrest:hamcrest:3.0")
}

spotless {
    java {
        eclipse()
    }
}
