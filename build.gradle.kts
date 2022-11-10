plugins {
    id("java")
    id("org.sonarqube") version "3.4.0.2513"
    id("application")
    id("com.github.johnrengelman.shadow") version "7.1.2"


}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

sonarqube {
    properties {
        property ("sonar.projectKey", "blagazsolt_temalabGradle")
        property ("sonar.organization", "blagazsolt")
        property ("sonar.host.url", "https://sonarcloud.io")
    }
}
application{
    mainClassName="main.Main"
}
allprojects {
    group = "hu.bme.mit.inf.theta"
    version = "3.2.1"
}
