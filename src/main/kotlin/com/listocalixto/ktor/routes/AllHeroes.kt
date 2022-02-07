package com.listocalixto.ktor.routes

import com.listocalixto.ktor.app.Constants.ENDPOINT_ALL_HEROES
import com.listocalixto.ktor.app.Constants.ERR_HEROES_NOT_FOUND
import com.listocalixto.ktor.app.Constants.ERR_INVALID_PAGE_NUMBER
import com.listocalixto.ktor.app.Constants.MAXIMUM_PAGE_VALUE
import com.listocalixto.ktor.app.Constants.MINIMUM_PAGE_VALUE
import com.listocalixto.ktor.models.ApiResponse
import com.listocalixto.ktor.repository.HeroRepo
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.lang.NumberFormatException

fun Route.getAllHeroes() {
    val heroRepo: HeroRepo by inject()

    get(ENDPOINT_ALL_HEROES) {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: MINIMUM_PAGE_VALUE
            require(page in MINIMUM_PAGE_VALUE..MAXIMUM_PAGE_VALUE)
            val apiResponse = heroRepo.getAllHeroes(page = page)
            call.respond(message = apiResponse, status = HttpStatusCode.OK)
        } catch (e: NumberFormatException) {
            call.respond(
                message = ApiResponse(success = false, message = ERR_INVALID_PAGE_NUMBER),
                status = HttpStatusCode.BadRequest
            )
        } catch (e: IllegalArgumentException) {
            call.respond(
                message = ApiResponse(success = false, message = ERR_HEROES_NOT_FOUND),
                status = HttpStatusCode.NotFound
            )
        }
    }
}