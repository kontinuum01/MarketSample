package ru.gb.android.marketsample.clean.features.products.data

import retrofit2.http.GET

interface ProductApiService {
    @GET("test_api_products.json")
    suspend fun getProducts(): List<ProductDto>
}
