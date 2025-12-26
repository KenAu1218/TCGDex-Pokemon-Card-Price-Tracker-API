package com.kenau.pokemoncard.server.controller

import com.kenau.pokemoncard.server.dto.TcgDexCard
import com.kenau.pokemoncard.server.dto.TcgDexCardBrief
import com.kenau.pokemoncard.server.dto.TcgDexSeriesBrief
import com.kenau.pokemoncard.server.dto.TcgDexSeriesDetailed
import com.kenau.pokemoncard.server.dto.TcgDexSetBrief
import com.kenau.pokemoncard.server.dto.TcgDexSetDetailed
import com.kenau.pokemoncard.server.entity.CardEntity
import com.kenau.pokemoncard.server.entity.SetEntity
import com.kenau.pokemoncard.server.repository.CardRepository
import com.kenau.pokemoncard.server.repository.SetRepository
import com.kenau.pokemoncard.server.service.TcgdexCardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PokemonCardController(private val service: TcgdexCardService, private val setRepo: SetRepository,
                            private val cardRepo: CardRepository
) {

    // ==========================================
    //  PART 1: LOCAL DATABASE (FAST & CHEAP)
    //  Use these for your Main App Screens
    // ==========================================

    // 1. Get All Sets (Database)
    // URL: http://localhost:8080/db/sets
    @GetMapping("/db/sets")
    fun getAllSetsFromDb(): List<SetEntity> {
        return setRepo.findAll()
    }

    // 2. Get Cards by Set ID (Database)
    // URL: http://localhost:8080/db/sets/{setId}/cards
    @GetMapping("/db/sets/{setId}/cards")
    fun getCardsFromDb(@PathVariable setId: String): List<CardEntity> {
        return cardRepo.findBySetId(setId)
    }

    // ==========================================
    //  PART 2: EXTERNAL API (SLOW & LIVE)
    //  Use these for specific searching or syncing
    // ==========================================

    @GetMapping("/card/{id}")
    fun getPKCard(@PathVariable id: String): TcgDexCard? {
        return service.getPKCard(id)
    }

    @GetMapping("/cards")
    fun searchCards(
        @RequestParam(required = false) name: String?,
        @RequestParam(defaultValue = "1") page: Int
    ): List<TcgDexCardBrief>? {
        return service.searchCards(name, page)
    }

    // 1. Get Set Details
    @GetMapping("/set/{id}")
    fun getSet(@PathVariable id: String): TcgDexSetDetailed? {
        return service.getSet(id)
    }

    // 2. Search Sets
    @GetMapping("/sets")
    fun searchSets(@RequestParam(required = false) name: String?): List<TcgDexSetBrief>? {
        return service.searchSets(name)
    }

    // NEW Endpoint
    // Browser URL: http://localhost:8080/set/swsh3/136
    @GetMapping("/set/{setId}/{localId}")
    fun getCardBySetAndLocalId(
        @PathVariable setId: String,
        @PathVariable localId: String
    ): TcgDexCard? {
        return service.getCardBySetAndLocalId(setId, localId)
    }

    // 1. Endpoint: Get Single Series
    // URL: http://localhost:8080/series/swsh
    @GetMapping("/series/{id}")
    fun getSeries(@PathVariable id: String): TcgDexSeriesDetailed? {
        return service.getSeries(id)
    }

    // 2. Endpoint: Search/List Series
    // URL: http://localhost:8080/series
    @GetMapping("/series")
    fun searchSeries(@RequestParam(required = false) name: String?): List<TcgDexSeriesBrief>? {
        return service.searchSeries(name)
    }
}