package com.kenau.pokemoncard.server.service

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.kenau.pokemoncard.server.entity.CardEntity
import com.kenau.pokemoncard.server.entity.SetEntity
import com.kenau.pokemoncard.server.repository.CardRepository
import com.kenau.pokemoncard.server.repository.SetRepository
import com.kenau.pokemoncard.server.util.UrlHelper
import jakarta.transaction.Transactional
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

@Service
class SyncService(
    private val setRepo: SetRepository,
    private val cardRepo: CardRepository
) {
    init {
        println("✅ SyncService has been loaded!")
    }
    private val restClient = RestClient.builder()
        .baseUrl("https://api.tcgdex.net/v2/en")
        .build()

//    @EventListener(ApplicationReadyEvent::class)
//    fun runOnStartup() {
//        println("🚀 Application Started! Triggering initial sync...")
//        syncAllSets()
//    }

    // --- AUTOMATIC SCHEDULER ---
    // Runs at 00:00:00am, on the 1st day of every 4th month
    // Cron format: Seconds Minutes Hours DayOfMonth Month DayOfWeek
    @Scheduled(cron = "0 0 0 1 */4 *")
    fun scheduledSync() {
        println("⏰ Starting Scheduled Database Sync...")
        syncAllSets()
    }

    // --- MAIN LOGIC ---
    @Transactional
    fun syncAllSets() {
        // 1. Get the big list of sets (The JSON you provided)
        val allSetsApi = restClient.get().uri("/sets").retrieve().body<List<ApiSetSummary>>() ?: return

        println("Found ${allSetsApi.size} sets from API.")

        // 2. Loop through each set
        for (apiSet in allSetsApi) {
            // Check if we already have this set in DB
            if (setRepo.existsById(apiSet.id)) {
                continue // Skip if already cached
            }

            println("⬇️ Downloading new set: ${apiSet.name}...")
            saveSetAndCards(apiSet)
        }
        println("✅ Sync Complete!")
    }

    private fun saveSetAndCards(summary: ApiSetSummary) {
        // A. Save the Set Entity
        val setEntity = SetEntity(
            id = summary.id,
            name = summary.name,
            logoUrl = UrlHelper.toWebpSymbol(summary.logo),
            symbolUrl = UrlHelper.toWebpSymbol(summary.symbol),
            cardCountTotal = summary.cardCount.total,
            cardCountOfficial = summary.cardCount.official
        )
        setRepo.save(setEntity)

        // B. Fetch the cards for this specific set
        // Endpoint: /sets/{id} returns the set details + card list
        val detailedSet = restClient.get().uri("/sets/${summary.id}").retrieve().body<ApiSetDetail>()

        // C. Save all cards
        detailedSet?.cards?.forEach { apiCard ->
            val cardEntity = CardEntity(
                id = apiCard.id,
                localId = apiCard.localId,
                name = apiCard.name,
                imageUrl = UrlHelper.toHighQualityCard(apiCard.image),
                set = setEntity, // Link to parent
            )
            cardRepo.save(cardEntity)
        }
    }
}

// --- HELPER DTOs FOR SYNC ONLY ---
// We define these here just to map the incoming JSON for the sync process
@JsonIgnoreProperties(ignoreUnknown = true)
data class ApiSetSummary(
    val id: String,
    val name: String,
    val logo: String?,
    val symbol: String?,
    val cardCount: ApiCount
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ApiCount(val total: Int, val official: Int)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ApiSetDetail(val cards: List<ApiCardBrief>)
@JsonIgnoreProperties(ignoreUnknown = true)
data class ApiCardBrief(val id: String, val localId: String, val name: String, val image: String?)