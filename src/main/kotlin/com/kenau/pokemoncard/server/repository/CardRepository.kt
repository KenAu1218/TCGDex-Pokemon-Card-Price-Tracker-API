package com.kenau.pokemoncard.server.repository

import com.kenau.pokemoncard.server.entity.CardEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CardRepository : JpaRepository<CardEntity, String> {

    // This allows us to say: "Give me all cards belonging to Set 'swsh3'"
    fun findBySetId(setId: String): List<CardEntity>
}