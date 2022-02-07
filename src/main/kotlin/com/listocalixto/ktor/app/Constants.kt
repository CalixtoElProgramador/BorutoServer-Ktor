package com.listocalixto.ktor.app

object Constants {

    const val KEY_PREV_PAGE = "prevPage"
    const val KEY_NEXT_PAGE = "nextPage"
    const val MINIMUM_PAGE_VALUE = 1
    const val MAXIMUM_PAGE_VALUE = 5
    const val MINIMUM_PREV_PAGE_VALUE = MINIMUM_PAGE_VALUE + 1
    const val MAXIMUM_NEXT_PAGE_VALUE = MAXIMUM_PAGE_VALUE - 1

    private const val BASE_URL = "/boruto"
    const val ENDPOINT_ALL_HEROES = "$BASE_URL/heroes"
    const val ENDPOINT_FIND_HERO_BY_NAME = "$ENDPOINT_ALL_HEROES/search"

    const val ERR_HEROES_NOT_FOUND = "Heroes not found."
    const val ERR_INVALID_PAGE_NUMBER = "Only numbers allowed."

    const val MSG_SUCCESS = "Ok"
    const val MSG_ENDPOINT_NON_EXISTING = "Page not founded"
    const val MSG_UNAUTHORIZED_USER = "Authentication Error: Verify your credentials"



}