/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("wikisearch.kotlin-library-conventions")
}

repositories {
    maven {
        url = uri("https://jitpack.io")
    }
    mavenCentral()
}

dependencies {
    api(project(":document"))
    implementation("com.londogard:smile-nlp-kt:1.1.0")
    implementation(group="xpp3", name="xpp3", version="1.1.4c")
}
