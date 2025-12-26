package com.kenau.pokemoncard.server.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

// Used for:
// 1. The result of "Search Sets" (/sets)
// 2. The nested "set" info inside a Card object
@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexSetBrief(
    val id: String,
    val name: String,

    @JsonProperty("logo")
    private val _logo: String?,

    @JsonProperty("symbol")
    private val _symbol: String?,

    val cardCount: TcgDexCardCount?
) {
    val logo: String?
        get() = _logo?.let { "$it.webp" }

    val symbol: String?
        get() = _symbol?.let { "$it.webp" }
}