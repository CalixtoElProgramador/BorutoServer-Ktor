package com.listocalixto.ktor.routes

import com.listocalixto.ktor.app.Constants.ENDPOINT_FIND_HERO_BY_NAME
import com.listocalixto.ktor.repository.HeroRepo
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.searchHeroes() {
    val heroRepo: HeroRepo by inject()

    get(ENDPOINT_FIND_HERO_BY_NAME) {
        val name = call.request.queryParameters["name"] ?: ""
        val apiResponse = heroRepo.searchHeroes(name)
        call.respond(message = apiResponse, status = HttpStatusCode.OK)
    }
}