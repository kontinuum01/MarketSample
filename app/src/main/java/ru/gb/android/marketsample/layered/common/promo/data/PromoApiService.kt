package ru.gb.android.marketsample.layered.common.promo.data

import retrofit2.http.GET

interface PromoApiService {
    @GET("test_api_promo.json")
    suspend fun getPromos(): List<PromoDto>
}
