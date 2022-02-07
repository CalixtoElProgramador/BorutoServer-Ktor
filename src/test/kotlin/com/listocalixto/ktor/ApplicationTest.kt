package com.listocalixto.ktor

import com.listocalixto.ktor.app.Constants.ENDPOINT_ALL_HEROES
import com.listocalixto.ktor.app.Constants.ENDPOINT_FIND_HERO_BY_NAME
import com.listocalixto.ktor.app.Constants.ERR_HEROES_NOT_FOUND
import com.listocalixto.ktor.app.Constants.ERR_INVALID_PAGE_NUMBER
import com.listocalixto.ktor.app.Constants.KEY_NEXT_PAGE
import com.listocalixto.ktor.app.Constants.KEY_PREV_PAGE
import com.listocalixto.ktor.app.Constants.MAXIMUM_PAGE_VALUE
import com.listocalixto.ktor.app.Constants.MSG_ENDPOINT_NON_EXISTING
import com.listocalixto.ktor.app.Constants.MSG_SUCCESS
import com.listocalixto.ktor.models.ApiResponse
import com.listocalixto.ktor.repository.HeroRepo
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import org.koin.java.KoinJavaComponent.inject
import kotlin.test.assertEquals

class ApplicationTest {

    private val heroRepo: HeroRepo by inject(HeroRepo::class.java)

    @Test
    fun `access root endpoint, assert correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/").run {
                assertEquals(expected = HttpStatusCode.OK, actual = response.status())
                assertEquals(expected = "Welcome to Boruto API", actual = response.content)
            }
        }
    }

    @Test
    fun `access all heroes endpoint, query all pages, assert correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            val pages = 1..5
            val heroes = listOf(
                heroRepo.page1,
                heroRepo.page2,
                heroRepo.page3,
                heroRepo.page4,
                heroRepo.page5
            )
            pages.forEach { page ->
                handleRequest(HttpMethod.Get, "$ENDPOINT_ALL_HEROES?page=$page").run {
                    assertEquals(expected = HttpStatusCode.OK, actual = response.status())
                    val expected = ApiResponse(
                        true,
                        MSG_SUCCESS,
                        prevPage = heroRepo.calculatePage(page = page)[KEY_PREV_PAGE],
                        nextPage = heroRepo.calculatePage(page = page)[KEY_NEXT_PAGE],
                        heroes = heroes[page - 1]
                    )
                    val actual = Json.decodeFromString<ApiResponse>(response.content.toString())
                    assertEquals(expected = expected, actual = actual)
                }
            }
        }
    }

    @Test
    fun `access all heroes endpoint, query page, assert correct information`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "$ENDPOINT_ALL_HEROES?page=2").run {
                assertEquals(expected = HttpStatusCode.OK, actual = response.status())
                val expected = ApiResponse(true, MSG_SUCCESS, 1, 3, heroRepo.page2)
                val actual = Json.decodeFromString<ApiResponse>(response.content.toString())
                assertEquals(expected = expected, actual = actual)
            }
        }
    }

    @Test
    fun `access all heroes endpoint, query non existing page number, assert error`() {
        withTestApplication(moduleFunction = Application::module) {
            val page = MAXIMUM_PAGE_VALUE + 1
            handleRequest(HttpMethod.Get, "$ENDPOINT_ALL_HEROES?page=$page").run {
                assertEquals(expected = HttpStatusCode.NotFound, actual = response.status())
                val expected = ApiResponse(false, ERR_HEROES_NOT_FOUND)
                val actual = Json.decodeFromString<ApiResponse>(response.content.toString())
                assertEquals(expected = expected, actual = actual)
            }
        }
    }

    @Test
    fun `access all heroes endpoint, query invalid page number, assert error`() {
        withTestApplication(moduleFunction = Application::module) {
            val page = "LoL"
            handleRequest(HttpMethod.Get, "$ENDPOINT_ALL_HEROES?page=$page").run {
                assertEquals(expected = HttpStatusCode.BadRequest, actual = response.status())
                val expected = ApiResponse(false, message = ERR_INVALID_PAGE_NUMBER)
                val actual = Json.decodeFromString<ApiResponse>(response.content.toString())
                assertEquals(expected = expected, actual = actual)
            }
        }
    }

    @Test
    fun `access find hero by name endpoint, assert correct information`() {
        val name = "sa"
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "$ENDPOINT_FIND_HERO_BY_NAME?name=$name").run {
                val expected = ApiResponse(true, message = MSG_SUCCESS, heroes = heroRepo.findHeroesByName(name))
                val actual = Json.decodeFromString<ApiResponse>(response.content.toString())
                assertEquals(expected = expected, actual = actual)
            }
        }
    }

    /**
     * In this test we are expecting only one result because we know in advance
     * that we are only going to receive one result. This test was previously
     * performed manually with the Postman software.
     *
     * @author Aaron Calixto
     */

    @Test
    fun `access find hero by name endpoint, query hero name, assert single hero result`() {
        val name = "sas"
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "$ENDPOINT_FIND_HERO_BY_NAME?name=$name").run {
                assertEquals(expected = HttpStatusCode.OK, actual = response.status())
                val actual = Json.decodeFromString<ApiResponse>(response.content.toString()).heroes.size
                assertEquals(expected = 1, actual = actual)
            }
        }
    }

    @Test
    fun `access find hero by name endpoint, query an empty text, assert a empty list as a result`() {
        val name = ""
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "$ENDPOINT_FIND_HERO_BY_NAME?name=$name").run {
                assertEquals(expected = HttpStatusCode.OK, actual = response.status())
                val actual = Json.decodeFromString<ApiResponse>(response.content.toString()).heroes
                assertEquals(expected = emptyList(), actual = actual)
            }
        }
    }

    @Test
    fun `access find hero by name endpoint, query non existing hero, assert a empty list as a result`() {
        val name = "unknown"
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "$ENDPOINT_FIND_HERO_BY_NAME?name=$name").run {
                assertEquals(expected = HttpStatusCode.OK, actual = response.status())
                val actual = Json.decodeFromString<ApiResponse>(response.content.toString()).heroes
                assertEquals(expected = emptyList(), actual = actual)
            }
        }
    }

    @Test
    fun `access non existing endpoint, assert not found`() {
        withTestApplication(moduleFunction = Application::module) {
            handleRequest(HttpMethod.Get, "/unknown").run {
                assertEquals(expected = HttpStatusCode.NotFound, actual = response.status())
                assertEquals(expected = MSG_ENDPOINT_NON_EXISTING, actual = response.content.toString())
            }
        }
    }

}