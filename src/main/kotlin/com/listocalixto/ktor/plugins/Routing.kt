package com.listocalixto.ktor.plugins

import com.listocalixto.ktor.routes.getAllHeroes
import com.listocalixto.ktor.routes.root
import io.ktor.application.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        root()
        getAllHeroes()
    }
}