package ru.gb.android.marketsample.layered

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gb.android.marketsample.layered.common.promo.domain.ConsumePromosUseCase
import ru.gb.android.marketsample.layered.features.products.domain.ConsumeProductsUseCase
import ru.gb.android.marketsample.layered.features.products.presentation.ProductVOFactory
import ru.gb.android.marketsample.layered.features.products.presentation.ProductsViewModel
import ru.gb.android.marketsample.layered.features.promo.presentation.PromoVOMapper
import ru.gb.android.marketsample.layered.features.promo.presentation.PromoViewModel

class ProductsViewModelFactory(
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val productVOFactory: ProductVOFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
    private val promoVOMapper: PromoVOMapper,
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ProductsViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return ProductsViewModel(
                    consumeProductsUseCase = consumeProductsUseCase,
                    productVOFactory = productVOFactory,
                    consumePromosUseCase = consumePromosUseCase,
                ) as T
            }

            modelClass.isAssignableFrom(PromoViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return PromoViewModel(
                    consumePromosUseCase = consumePromosUseCase,
                    promoVOMapper = promoVOMapper,
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
