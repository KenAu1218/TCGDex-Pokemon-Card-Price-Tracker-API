package com.kenau.pokemoncard.server.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexSeriesDetailed(
    val id: String,
    val name: String,

    @JsonProperty("logo")
    private val _logo: String?,

    // Since TcgDexSetBrief is already updated (in previous step),
    // the logos inside this list will work automatically!
    val sets: List<TcgDexSetBrief>?
) {
    val logo: String?
        get() = _logo?.let { "$it.webp" }
}