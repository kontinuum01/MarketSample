package ru.gb.android.marketsample.start.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.gb.android.marketsample.start.data.api.PromoApiService
import ru.gb.android.marketsample.start.presentation.PromoEntity
import ru.gb.android.marketsample.start.data.storage.PromoLocalDataSource

class PromoRepository(
    private val promoLocalDataSource: PromoLocalDataSource,
    private val promoApiService: PromoApiService,
    private val coroutineDispatcher: CoroutineDispatcher,
) {
    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

    fun consumePromos(): Flow<List<PromoEntity>> {
        scope.launch {
            val promos = promoApiService.getPromos()
            promoLocalDataSource.savePromos(
                promos.map { promoDto ->
                    PromoEntity(
                        id = promoDto.id,
                        name = promoDto.name,
                        image = promoDto.image,
                        discount = promoDto.discount,
                        description = promoDto.description,
                        type = promoDto.type,
                        products = promoDto.products ?: emptyList(),
                    )
                }
            )
        }

        return promoLocalDataSource.consumeProducts()
            .flowOn(coroutineDispatcher)
    }
}
