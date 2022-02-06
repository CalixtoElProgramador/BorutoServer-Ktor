package com.listocalixto.ktor.di

import com.listocalixto.ktor.repository.HeroRepo
import com.listocalixto.ktor.repository.HeroRepoImpl
import org.koin.dsl.module

val koinModule = module { single<HeroRepo> { HeroRepoImpl() } }