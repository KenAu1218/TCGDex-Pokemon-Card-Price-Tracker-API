package com.kenau.pokemoncard.server.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexCardCount(
    val official: Int,
    val total: Int,
    // These specific counts only appear in the "Get Set" API, not the Card API
    val normal: Int? = null,
    val reverse: Int? = null,
    val holo: Int? = null,
    @JsonProperty("firstEd") val firstEd: Int? = null
)