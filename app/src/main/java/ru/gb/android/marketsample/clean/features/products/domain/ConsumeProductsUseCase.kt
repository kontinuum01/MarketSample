package ru.gb.android.marketsample.clean.features.products.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.gb.android.marketsample.clean.features.products.data.ProductDomainMapper
import ru.gb.android.marketsample.clean.features.products.data.ProductRepositoryImpl

class ConsumeProductsUseCase(
    private val productRepository: ProductRepositoryImpl,
    private val productDomainMapper: ProductDomainMapper,
) {
    operator fun invoke(): Flow<List<Product>> {
        return productRepository.consumeProducts()
            .map{ products -> products.map(productDomainMapper::fromEntity) }
    }
}