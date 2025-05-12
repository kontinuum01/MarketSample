package ru.gb.android.marketsample.clean.common.promo.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.gb.android.marketsample.clean.common.promo.data.PromoDomainMapper
import ru.gb.android.marketsample.clean.common.promo.data.PromoRepositoryImpl

class ConsumePromosUseCase(
    private val promoRepository: PromoRepositoryImpl,
    private val promoDomainMapper: PromoDomainMapper,
) {
    operator fun invoke(): Flow<List<Promo>> {
        return promoRepository.consumePromos()
            .map{ promos -> promos.map(promoDomainMapper::fromEntity) }
    }
}