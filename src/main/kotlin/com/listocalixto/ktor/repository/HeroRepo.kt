package com.listocalixto.ktor.repository

import com.listocalixto.ktor.models.ApiResponse
import com.listocalixto.ktor.models.Hero

interface HeroRepo {
    val heroes: Map<Int, List<Hero>>

    val page1: List<Hero>
    val page2: List<Hero>
    val page3: List<Hero>
    val page4: List<Hero>
    val page5: List<Hero>

    suspend fun getAllHeroes(page: Int = 1): ApiResponse
    suspend fun searchHeroes(name: String): ApiResponse
    fun calculatePage(page: Int): Map<String, Int?>
    fun findHeroesByName(query: String?): List<Hero>
}