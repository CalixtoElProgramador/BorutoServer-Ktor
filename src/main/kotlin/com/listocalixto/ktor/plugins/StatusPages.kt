package com.listocalixto.ktor.plugins

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import javax.naming.AuthenticationException

fun Application.configureStatusPages() {
    install(StatusPages) {
        val notFound = HttpStatusCode.NotFound
        status(notFound) {
            call.respond(
                message = "Page not founded",
                status = notFound
            )
        }
        exception<AuthenticationException> {
            call.respond(
                message = "Authentication Error: Verify your credentials",
                status = HttpStatusCode.Unauthorized
            )
        }
    }
}