package ru.gb.android.marketsample.layered.features.products.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductRepository(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val productDataMapper: ProductDataMapper,
    private val coroutineScope: CoroutineScope,
) {
    fun consumeProducts(): Flow<List<ProductEntity>> {
        coroutineScope.launch(Dispatchers.IO) {
            val products = productRemoteDataSource.getProducts()
            productLocalDataSource.saveProducts(
                products.map(productDataMapper::toEntity)
            )
        }
        return productLocalDataSource.consumeProducts()
    }
}
