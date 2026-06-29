plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
}

group = "org.mohsen.reviewtask"
version = "1.0.0"
application {
    mainClass = "org.mohsen.reviewtask.ApplicationKt"
}

dependencies {
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
}