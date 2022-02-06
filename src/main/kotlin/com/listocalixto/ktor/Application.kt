package com.listocalixto.ktor

import com.listocalixto.ktor.plugins.*
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8081, module = Application::module).start(true)

}

fun Application.module() {
    configureKoin()
    configureDefaultHeader()
    configureMonitoring()
    configureRouting()
    configureSerialization()
}