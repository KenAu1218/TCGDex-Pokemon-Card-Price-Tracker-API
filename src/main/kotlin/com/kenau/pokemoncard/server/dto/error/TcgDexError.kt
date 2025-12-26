package com.kenau.pokemoncard.server.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexError(
    val type: String?,
    val title: String?,
    val status: Int?,
    val endpoint: String?,
    val method: String?,
    // Specific to Language Invalid Error
    val lang: String? = null,
    val details: String? = null
)