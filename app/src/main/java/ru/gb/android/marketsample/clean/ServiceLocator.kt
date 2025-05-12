package ru.gb.android.marketsample.clean

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.gb.android.marketsample.clean.common.promo.data.PromoApiService
import ru.gb.android.marketsample.clean.common.promo.data.PromoDataMapper
import ru.gb.android.marketsample.clean.common.promo.data.PromoLocalDataSource
import ru.gb.android.marketsample.clean.common.promo.data.PromoRemoteDataSource
import ru.gb.android.marketsample.clean.common.promo.data.PromoRepositoryImpl
import ru.gb.android.marketsample.clean.common.promo.domain.ConsumePromosUseCase
import ru.gb.android.marketsample.clean.common.promo.data.PromoDomainMapper
import ru.gb.android.marketsample.clean.features.products.data.ProductApiService
import ru.gb.android.marketsample.clean.features.products.data.ProductDataMapper
import ru.gb.android.marketsample.clean.features.products.data.ProductLocalDataSource
import ru.gb.android.marketsample.clean.features.products.data.ProductRemoteDataSource
import ru.gb.android.marketsample.clean.features.products.data.ProductRepositoryImpl
import ru.gb.android.marketsample.clean.features.products.domain.ConsumeProductsUseCase
import ru.gb.android.marketsample.clean.features.products.data.ProductDomainMapper
import ru.gb.android.marketsample.clean.features.products.presentation.ProductVOFactory
import ru.gb.android.marketsample.clean.features.promo.presentation.PromoVOMapper


object ServiceLocator {

    private const val ENDPOINT = "https://makzimi.github.io/"

    lateinit var applicationContext: Context

    private var productRepositorySingleton: ProductRepositoryImpl? = null
    private var promoRepositorySingleton: PromoRepositoryImpl? = null
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

    private fun providePromoRepository(): PromoRepositoryImpl {
        val local = promoRepositorySingleton
        return local ?: run {
            val newPromoRepository = PromoRepositoryImpl(
                promoLocalDataSource = providePromoLocalDataSource(),
                promoRemoteDataSource = providePromoRemoteDataSource(),
                promoDataMapper = providePromoDataMapper(),
                coroutineDispatcher = provideIOCoroutineDispatcher(),
            )
            promoRepositorySingleton = newPromoRepository
            newPromoRepository
        }
    }

    private fun providePromoLocalDataSource(): PromoLocalDataSource {
        return PromoLocalDataSource(
            dataStore = ServiceLocator.applicationContext.appDataStore,
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

    private fun provideProductRepository(): ProductRepositoryImpl {
        val local = productRepositorySingleton
        return local ?: run {
            val newProductRepository = ProductRepositoryImpl(
                productLocalDataSource = provideProductLocalDataSource(),
                productRemoteDataSource = provideProductRemoteDataSource(),
                productDataMapper = provideProductDataMapper(),
                coroutineDispatcher = provideIOCoroutineDispatcher(),
            )
            productRepositorySingleton = newProductRepository
            newProductRepository
        }
    }

    private fun provideIOCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
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
                .baseUrl(ServiceLocator.ENDPOINT)
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
            dataStore = ServiceLocator.applicationContext.appDataStore,
        )
    }

    private val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "layered_app")
}

