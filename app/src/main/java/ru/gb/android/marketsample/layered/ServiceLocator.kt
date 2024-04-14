package ru.gb.android.marketsample.layered

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.android.marketsample.layered.common.promo.data.PromoApiService
import ru.gb.android.marketsample.layered.common.promo.data.PromoDataMapper
import ru.gb.android.marketsample.layered.common.promo.data.PromoLocalDataSource
import ru.gb.android.marketsample.layered.common.promo.data.PromoRemoteDataSource
import ru.gb.android.marketsample.layered.common.promo.data.PromoRepository
import ru.gb.android.marketsample.layered.common.promo.domain.ConsumePromosUseCase
import ru.gb.android.marketsample.layered.common.promo.domain.PromoDomainMapper
import ru.gb.android.marketsample.layered.features.products.domain.ProductDomainMapper
import ru.gb.android.marketsample.layered.features.products.data.ProductApiService
import ru.gb.android.marketsample.layered.features.products.data.ProductDataMapper
import ru.gb.android.marketsample.layered.features.products.data.ProductLocalDataSource
import ru.gb.android.marketsample.layered.features.products.data.ProductRemoteDataSource
import ru.gb.android.marketsample.layered.features.products.data.ProductRepository
import ru.gb.android.marketsample.layered.features.products.domain.ConsumeProductsUseCase
import ru.gb.android.marketsample.layered.features.products.presentation.ProductVOFactory
import ru.gb.android.marketsample.layered.features.promo.presentation.PromoVOMapper

object ServiceLocator {

    const val ENDPOINT = "https://makzimi.github.io/"

    lateinit var applicationContext: Context

    private var productRepositorySingleton: ProductRepository? = null
    private var promoRepositorySingleton: PromoRepository? = null
    private var retrofitSingleton: Retrofit? = null

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ProductsViewModelFactory(
            consumeProductsUseCase = provideConsumeProductsUseCase(),
            productVOFactory = provideProductVOMapper(),
            consumePromosUseCase = provideConsumePromosUseCase(),
            promoVOMapper = providePromoVOMapper(),
        )
    }

    private fun provideConsumePromosUseCase(): ConsumePromosUseCase {
        return ConsumePromosUseCase(
            promoRepository = providePromoRepository(),
            promoDomainMapper = providePromoDomainMapper(),
        )
    }

    private fun providePromoRepository(): PromoRepository {
        val local = promoRepositorySingleton
        return local ?: run {
            val newPromoRepository = PromoRepository(
                promoLocalDataSource = providePromoLocalDataSource(),
                promoRemoteDataSource = providePromoRemoteDataSource(),
                promoDataMapper = providePromoDataMapper(),
                coroutineScope = provideCoroutineScope(),
            )
            promoRepositorySingleton = newPromoRepository
            newPromoRepository
        }
    }

    private fun providePromoLocalDataSource(): PromoLocalDataSource {
        return PromoLocalDataSource(
            dataStore = applicationContext.appDataStore,
        )
    }

    private fun providePromoRemoteDataSource(): PromoRemoteDataSource {
        return PromoRemoteDataSource(
            promoApiService = providePromoApiService(),
        )
    }

    private fun providePromoApiService(): PromoApiService {
        return provideRetrofit().create(PromoApiService::class.java)
    }

    private fun providePromoDataMapper(): PromoDataMapper {
        return PromoDataMapper()
    }

    private fun provideConsumeProductsUseCase(): ConsumeProductsUseCase {
        return ConsumeProductsUseCase(
            productRepository = provideProductRepository(),
            productDomainMapper = provideProductDomainMapper()
        )
    }

    private fun provideProductRepository(): ProductRepository {
        val local = productRepositorySingleton
        return local ?: run {
            val newProductRepository = ProductRepository(
                productLocalDataSource = provideProductLocalDataSource(),
                productRemoteDataSource = provideProductRemoteDataSource(),
                productDataMapper = provideProductDataMapper(),
                coroutineScope = provideCoroutineScope(),
            )
            productRepositorySingleton = newProductRepository
            newProductRepository
        }
    }

    private fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob())
    }

    private fun provideProductRemoteDataSource(): ProductRemoteDataSource {
        return ProductRemoteDataSource(
            productApiService = provideProductApiService(),
        )
    }

    private fun provideProductDataMapper(): ProductDataMapper {
        return ProductDataMapper()
    }

    private fun provideProductDomainMapper(): ProductDomainMapper {
        return ProductDomainMapper()
    }

    private fun provideProductVOMapper(): ProductVOFactory {
        return ProductVOFactory()
    }

    private fun providePromoVOMapper(): PromoVOMapper {
        return PromoVOMapper()
    }

    private fun providePromoDomainMapper(): PromoDomainMapper {
        return PromoDomainMapper()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    private fun provideRetrofit(): Retrofit {
        val local = retrofitSingleton
        return local ?: run {
            val newRetrofit = Retrofit.Builder()
                .client(provideOkHttpClient())
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitSingleton = newRetrofit
            newRetrofit
        }
    }

    private fun provideProductApiService(): ProductApiService {
        return provideRetrofit().create(ProductApiService::class.java)
    }

    private fun provideProductLocalDataSource(): ProductLocalDataSource {
        return ProductLocalDataSource(
            dataStore = applicationContext.appDataStore,
        )
    }

    private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "layered_app")
}
