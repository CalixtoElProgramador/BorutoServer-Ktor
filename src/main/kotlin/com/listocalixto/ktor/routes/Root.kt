package com.listocalixto.ktor.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import javax.naming.AuthenticationException

fun Route.root() {
    get("/") {
        call.respond(
            message = "Welcome to Boruto API",
            status = HttpStatusCode.OK
        )
    }
}