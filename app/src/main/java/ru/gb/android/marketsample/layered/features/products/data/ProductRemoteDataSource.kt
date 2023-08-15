package ru.gb.android.marketsample.layered.features.products.data

class ProductRemoteDataSource(
    private val productApiService: ProductApiService,
) {
    suspend fun getProducts(): List<ProductDto> {
        return productApiService.getProducts()
    }
}
