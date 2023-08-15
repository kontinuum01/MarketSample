package ru.gb.android.marketsample.start.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.gb.android.marketsample.start.data.api.ProductApiService
import ru.gb.android.marketsample.start.presentation.ProductEntity
import ru.gb.android.marketsample.start.data.storage.ProductLocalDataSource

class ProductRepository(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productApiService: ProductApiService,
    private val coroutineScope: CoroutineScope,
) {
    fun consumeProducts(): Flow<List<ProductEntity>> {
        coroutineScope.launch(Dispatchers.IO) {
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
    }
}
