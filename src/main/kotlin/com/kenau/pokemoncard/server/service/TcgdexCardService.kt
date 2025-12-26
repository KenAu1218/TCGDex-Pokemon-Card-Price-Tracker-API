package com.kenau.pokemoncard.server.service

import com.kenau.pokemoncard.server.dto.TcgDexCard
import com.kenau.pokemoncard.server.dto.TcgDexCardBrief
import com.kenau.pokemoncard.server.dto.TcgDexError
import com.kenau.pokemoncard.server.dto.TcgDexSeriesBrief
import com.kenau.pokemoncard.server.dto.TcgDexSeriesDetailed
import com.kenau.pokemoncard.server.dto.TcgDexSetBrief
import com.kenau.pokemoncard.server.dto.TcgDexSetDetailed
import com.kenau.pokemoncard.server.exception.TcgDexApiException
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import tools.jackson.databind.ObjectMapper

@Service
class TcgdexCardService(private val objectMapper: ObjectMapper) {

    init {
        println("✅ PokemonCardService has been loaded!")
    }
    private val restClient = RestClient.builder()
        .baseUrl("https://api.tcgdex.net/v2/en")
        // 1. CHANGE: Use defaultStatusHandler instead of onStatus
        .defaultStatusHandler(HttpStatusCode::isError) { request, response ->

            // 2. Read the error JSON
            val errorInfo = objectMapper.readValue(response.body, TcgDexError::class.java)

            if (errorInfo != null) {
                throw TcgDexApiException(errorInfo)
            } else {
                throw RuntimeException("Unknown error: ${response.statusCode}")
            }
        }
        .build()

//    private val restClient = RestClient.builder()
//        .baseUrl("https://api.tcgdex.net/v2/en")
//        .build()

    fun getPKCard(id: String): TcgDexCard? {
        return restClient.get()
            .uri("/cards/$id")
            .retrieve()
            .body<TcgDexCard>()
    }
    fun searchCards(query: String?, page: Int): List<TcgDexCardBrief>? {
        return restClient.get()
            .uri { uriBuilder ->
                val builder = uriBuilder.path("/cards")

                // 1. Add Name Filter (if user provided one)
                if (!query.isNullOrBlank()) {
                    builder.queryParam("name", query) // Defaults to "like" (laxist) match
                }

                // 2. Add Pagination (API requires "pagination:page" and "pagination:itemsPerPage")
                builder.queryParam("pagination:page", page)
                builder.queryParam("pagination:itemsPerPage", 20) // Fixed to 20 results per page

                builder.build()
            }
            .retrieve()
            .body<List<TcgDexCardBrief>>() // Expect a LIST of brief cards
    }

    // 1. Get Detailed Set
    fun getSet(id: String): TcgDexSetDetailed? {
        return restClient.get()
            .uri("/sets/$id")
            .retrieve()
            .body<TcgDexSetDetailed>()
    }

    // 2. Search Sets (Return list of Briefs)
    fun searchSets(name: String?): List<TcgDexSetBrief>? {
        return restClient.get()
            .uri { uriBuilder ->
                val builder = uriBuilder.path("/sets")
                if (!name.isNullOrBlank()) {
                    builder.queryParam("name", name)
                }
                builder.build()
            }
            .retrieve()
            .body<List<TcgDexSetBrief>>()
    }

    // NEW: Get Card by Set ID and Local ID
    // URL being called: https://api.tcgdex.net/v2/en/sets/swsh3/136
    fun getCardBySetAndLocalId(setId: String, localId: String): TcgDexCard? {
        return restClient.get()
            .uri("/sets/$setId/$localId")
            .retrieve()
            .body<TcgDexCard>()
    }

    // 1. Get Single Series (e.g., "swsh")
    fun getSeries(id: String): TcgDexSeriesDetailed? {
        return restClient.get()
            .uri("/series/$id")
            .retrieve()
            .body<TcgDexSeriesDetailed>()
    }

    // 2. Search/List Series (e.g., list all series)
    fun searchSeries(name: String?): List<TcgDexSeriesBrief>? {
        return restClient.get()
            .uri { uriBuilder ->
                val builder = uriBuilder.path("/series")
                // Add name filter if provided
                if (!name.isNullOrBlank()) {
                    builder.queryParam("name", name)
                }
                builder.build()
            }
            .retrieve()
            .body<List<TcgDexSeriesBrief>>()
    }
}