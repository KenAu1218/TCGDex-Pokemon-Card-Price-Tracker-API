package com.kenau.pokemoncard.server.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

// Used for: "Get Set" (/sets/{id})
@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexSetDetailed(
    val id: String,
    val name: String,
    val logo: String?,
    val symbol: String?,
    val cardCount: TcgDexCardCount?,

    // Extra fields that only exist in the Detailed view:
    val releaseDate: String?,
    val legal: TcgDexLegal?, // Reusing your existing Legal class
    val cards: List<TcgDexCardBrief> // Reusing your existing CardBrief class
)