package com.listocalixto.ktor.routes

import com.listocalixto.ktor.repository.HeroRepo
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.searchHeroes() {
    val heroRepo: HeroRepo by inject()

    get("/boruto/heroes/search") {
        val name = call.request.queryParameters["name"] ?: ""
        val apiResponse = heroRepo.searchHeroes(name)
        call.respond(message = apiResponse, status = HttpStatusCode.OK)
    }
}