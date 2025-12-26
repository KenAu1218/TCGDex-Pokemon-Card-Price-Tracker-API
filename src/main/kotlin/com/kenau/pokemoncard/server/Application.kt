package com.kenau.pokemoncard.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling // <--- Add this annotation
class PokemonServerApplication

fun main(args: Array<String>) {
	runApplication<PokemonServerApplication>(*args)
}