plugins {
    id("java")
    application
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
    testImplementation(platform("org.junit:junit-bom:5.14.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.hamcrest:hamcrest:3.0")
    testImplementation("org.apache.beam:beam-sdks-java-testing-junit:2.71.0")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}
