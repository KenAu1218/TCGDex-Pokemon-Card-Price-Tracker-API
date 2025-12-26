package com.kenau.pokemoncard.server.entity

import jakarta.persistence.*

@Entity
@Table(name = "cards")
data class CardEntity(
    @Id
    val id: String,

    @Column(name = "local_id")
    val localId: String,

    val name: String,

    @Column(name = "image_url")
    val imageUrl: String?,

    // We store the Set ID so we can query "Find all cards in Set X"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "set_id")
    val set: SetEntity,

)