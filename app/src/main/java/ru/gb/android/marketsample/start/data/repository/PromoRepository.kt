package ru.gb.android.marketsample.start.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.gb.android.marketsample.start.data.api.PromoApiService
import ru.gb.android.marketsample.start.presentation.PromoEntity
import ru.gb.android.marketsample.start.data.storage.PromoLocalDataSource

class PromoRepository(
    private val promoLocalDataSource: PromoLocalDataSource,
    private val promoApiService: PromoApiService,
    private val coroutineScope: CoroutineScope,
) {
    fun consumePromos(): Flow<List<PromoEntity>> {
        coroutineScope.launch(Dispatchers.IO) {
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
    }
}
