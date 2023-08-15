package ru.gb.android.marketsample.layered.common.promo.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PromoRepository(
    private val promoLocalDataSource: PromoLocalDataSource,
    private val promoRemoteDataSource: PromoRemoteDataSource,
    private val promoDataMapper: PromoDataMapper,
    private val coroutineScope: CoroutineScope,
) {
    fun consumePromos(): Flow<List<PromoEntity>> {
        coroutineScope.launch(Dispatchers.IO) {
            val promos = promoRemoteDataSource.getPromos()
            promoLocalDataSource.savePromos(
                promos.map(promoDataMapper::toEntity)
            )
        }
        return promoLocalDataSource.consumeProducts()
    }
}
