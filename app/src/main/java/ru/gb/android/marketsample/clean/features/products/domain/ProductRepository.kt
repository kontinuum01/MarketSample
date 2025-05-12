package ru.gb.android.marketsample.clean.features.products.domain

import kotlinx.coroutines.flow.Flow
import ru.gb.android.marketsample.clean.features.products.data.ProductEntity

interface ProductRepository {
    fun consumeProducts(): Flow<List<ProductEntity>>
}
