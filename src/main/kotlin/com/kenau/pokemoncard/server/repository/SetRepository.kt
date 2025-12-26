package com.kenau.pokemoncard.server.repository

import com.kenau.pokemoncard.server.entity.SetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SetRepository : JpaRepository<SetEntity, String>