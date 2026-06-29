plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktorServer)
}

group = "org.mohsen.reviewtask"
version = "1.0.0"
application {
    mainClass = "org.mohsen.reviewtask.ApplicationKt"
}

dependencies {
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
}