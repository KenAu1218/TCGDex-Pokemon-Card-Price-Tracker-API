package com.kenau.pokemoncard.server.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexCardBrief(
    val id: String,
    val localId: String,
    val name: String,

    @JsonProperty("image")
    private val _image: String?
) {
    val image: String?
        get() = _image?.let { "$it/high.webp" }
}