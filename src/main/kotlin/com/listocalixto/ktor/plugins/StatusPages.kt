package com.listocalixto.ktor.plugins

import com.listocalixto.ktor.app.Constants.MSG_ENDPOINT_NON_EXISTING
import com.listocalixto.ktor.app.Constants.MSG_UNAUTHORIZED_USER
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
                message = MSG_ENDPOINT_NON_EXISTING,
                status = notFound
            )
        }
        exception<AuthenticationException> {
            call.respond(
                message = MSG_UNAUTHORIZED_USER,
                status = HttpStatusCode.Unauthorized
            )
        }
    }
}