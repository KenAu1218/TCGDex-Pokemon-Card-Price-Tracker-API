package com.kenau.pokemoncard.server.exception

import com.kenau.pokemoncard.server.dto.TcgDexError

class TcgDexApiException(val error: TcgDexError) : RuntimeException(error.title)