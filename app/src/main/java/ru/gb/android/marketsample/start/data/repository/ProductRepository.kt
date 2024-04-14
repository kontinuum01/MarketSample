package ru.gb.android.marketsample.start.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.gb.android.marketsample.start.data.api.ProductApiService
import ru.gb.android.marketsample.start.presentation.ProductEntity
import ru.gb.android.marketsample.start.data.storage.ProductLocalDataSource

class ProductRepository(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productApiService: ProductApiService,
    private val coroutineDispatcher: CoroutineDispatcher,
) {
    private val scope = CoroutineScope(SupervisorJob() + coroutineDispatcher)

    fun consumeProducts(): Flow<List<ProductEntity>> {
        scope.launch {
            val products = productApiService.getProducts()

            productLocalDataSource.saveProducts(
                products.map { productDto ->
                    ProductEntity(
                        id = productDto.id,
                        name = productDto.name,
                        image = productDto.image,
                        price = productDto.price,
                        hasDiscount = false,
                        discount = 0,
                    )
                }
            )
        }

        return productLocalDataSource.consumeProducts()
            .flowOn(coroutineDispatcher)
    }
}
