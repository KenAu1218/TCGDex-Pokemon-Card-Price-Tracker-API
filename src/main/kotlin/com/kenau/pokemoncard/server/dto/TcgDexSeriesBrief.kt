package com.kenau.pokemoncard.server.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexSeriesBrief(
    val id: String,
    val name: String,

    // Rename raw field to _logo
    @JsonProperty("logo")
    private val _logo: String?
) {
    // Public getter adds the extension
    val logo: String?
        get() = _logo?.let { "$it.webp" }
}