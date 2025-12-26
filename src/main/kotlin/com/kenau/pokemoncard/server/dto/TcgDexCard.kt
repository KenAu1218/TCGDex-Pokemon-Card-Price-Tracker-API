package com.kenau.pokemoncard.server.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexCard(
    val id: String,
    val localId: String?,
    val name: String,
    @JsonProperty("image")
    private val _image: String?,
    val category: String?,
    val illustrator: String?,
    val rarity: String?,

    // Stats
    val hp: Int?,
    val retreat: Int?,
    val stage: String?,
    val dexId: List<Int>?,
    val types: List<String>?,
    val evolveFrom: String?,
    val description: String?,

    // Nested Objects
    val set: TcgDexSet?,
    val variants: TcgDexVariants?,
    val attacks: List<TcgDexAttack>?,
    val weaknesses: List<TcgDexWeakness>?,
    val legal: TcgDexLegal?,

    // PRICING (The important new part)
    val pricing: TcgDexPricing?
){
    val image: String?
        get() = _image?.let { "$it/high.webp" }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexSet(
    val id: String,
    val name: String,
    val logo: String?,
    val symbol: String?,
    val cardCount: TcgDexCardCount?
)


@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexVariants(
    val normal: Boolean?,
    val reverse: Boolean?,
    val holo: Boolean?,
    val firstEdition: Boolean?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexAttack(
    val name: String,
    val cost: List<String>?,
    val damage: Any?, // Use Any? because sometimes it's 90 (Int) and sometimes "10+" (String)
    val effect: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexWeakness(
    val type: String,
    val value: String?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexLegal(
    val standard: Boolean?,
    val expanded: Boolean?
)

// --- PRICING CLASSES ---

@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexPricing(
    val cardmarket: TcgDexCardMarket?,
    val tcgplayer: TcgDexTcgPlayer?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexCardMarket(
    val updated: String?,
    val unit: String?,
    val avg: Double?,
    val low: Double?,
    val trend: Double?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexTcgPlayer(
    val updated: String?,
    val unit: String?,
    val normal: TcgDexPriceEntry?,

    // We need @JsonProperty because Kotlin variables can't have hyphens (-)
    @JsonProperty("reverse-holofoil")
    val reverseHolofoil: TcgDexPriceEntry?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class TcgDexPriceEntry(
    val lowPrice: Double?,
    val midPrice: Double?,
    val highPrice: Double?,
    val marketPrice: Double?,
    val directLowPrice: Double?
)