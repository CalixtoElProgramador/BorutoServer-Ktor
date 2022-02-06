package com.listocalixto.ktor.routes

import com.listocalixto.ktor.models.ApiResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import java.lang.NumberFormatException

fun Route.getAllHeroes() {
    get("/boruto/heroes") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1.. 5)
            call.respond(message = page)
        } catch (e: NumberFormatException) {
            call.respond(
                message = ApiResponse(success = false, message = "Only numbers allowed."),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: IllegalArgumentException) {
            call.respond(
                message = ApiResponse(success = false, message = "Heroes not found."),
                status = HttpStatusCode.NotFound
            )
        }
    }
}