package com.kenau.pokemoncard.server.util

object UrlHelper {

    // For Cards: https://.../136 -> https://.../136/high.webp
    fun toHighQualityCard(url: String?): String? {
        if (url == null) return null
        if (url.endsWith(".webp")) return url // Already fixed
        return "$url/high.webp"
    }

    // For Sets: https://.../symbol -> https://.../symbol.webp
    fun toWebpSymbol(url: String?): String? {
        if (url == null) return null
        if (url.endsWith(".webp")) return url // Already fixed
        return "$url.webp"
    }
}