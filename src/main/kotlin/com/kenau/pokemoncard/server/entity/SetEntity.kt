package com.kenau.pokemoncard.server.entity

import jakarta.persistence.*

@Entity
@Table(name = "sets")
data class SetEntity(
    @Id
    val id: String,
    val name: String,
    @Column(name = "logo_url") val logoUrl: String?,
    @Column(name = "symbol_url") val symbolUrl: String?,
    @Column(name = "card_count_total") val cardCountTotal: Int,
    @Column(name = "card_count_official") val cardCountOfficial: Int
)

