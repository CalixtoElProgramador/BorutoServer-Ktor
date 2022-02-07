package com.listocalixto.ktor.plugins

import com.listocalixto.ktor.routes.getAllHeroes
import com.listocalixto.ktor.routes.root
import com.listocalixto.ktor.routes.searchHeroes
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*
import javax.naming.AuthenticationException

fun Application.configureRouting() {
    routing {
        root()
        getAllHeroes()
        searchHeroes()
        static("/images") { resources("images") }

        get("/boruto/auth") {
            throw AuthenticationException("Invalid user")
        }

    }
}