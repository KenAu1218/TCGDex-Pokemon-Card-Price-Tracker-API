package com.kenau.pokemoncard.server.exception

import com.kenau.pokemoncard.server.dto.TcgDexError
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(TcgDexApiException::class)
    fun handleTcgDexException(ex: TcgDexApiException): ResponseEntity<TcgDexError> {
        // Return the exact error object we got from TCGDex, with the same status code
        return ResponseEntity
            .status(ex.error.status ?: 500)
            .body(ex.error)
    }
}