plugins {
    application
    kotlin("jvm") version "1.5.10"
    kotlin("plugin.serialization") version "1.5.10"
}

group = "com.listocalixto.ktor"
version = "0.0.1"

application {
    mainClass.set("com.listocalixto.ktor.ApplicationKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("io.ktor:ktor-server-core:1.6.7")
    implementation("io.ktor:ktor-serialization:1.6.7")
    implementation("io.ktor:ktor-server-netty:1.6.7")

    //Logback
    implementation("ch.qos.logback:logback-classic:1.2.10")

    //Koin
    implementation("io.insert-koin:koin-ktor:3.1.4")
    implementation("io.insert-koin:koin-logger-slf4j:3.1.4")

    //Test
    testImplementation("io.ktor:ktor-server-tests:1.6.7")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.6.0")
}